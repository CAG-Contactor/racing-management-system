package se.cag.labs.pisensor.component;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import retrofit2.Call;
import retrofit2.Response;
import se.cag.labs.pisensor.contract.SensorId;
import se.cag.labs.pisensor.service.DroidRaceApiService;

import java.io.IOException;


@Component
@Slf4j
public class RegisterSensorsComponent {

    private DroidRaceApiService droidRaceApiService;

    @Autowired
    public RegisterSensorsComponent(DroidRaceApiService droidRaceApiService) {
        this.droidRaceApiService = droidRaceApiService;
    }

    /**
     * Adds ourself as a listener for a GPIO port
     *
     * @param sensorId the sensor to listen to
     */
    private void registerListenerForSensor(final SensorId sensorId) {
        final GpioController gpio = GpioFactory.getInstance();
        final GpioPinDigitalInput pinDigitalInput = gpio.provisionDigitalInputPin(sensorId.getPin());

        pinDigitalInput.addListener(new GpioPinListenerDigital() {
            @Override
            public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
                if (shouldSendEvent(event, sensorId)) {
                    long timestamp = System.currentTimeMillis();
                    log.info(" --> CHANGE ON " + sensorId + " - TIME: " + timestamp);
                    registerEvent(sensorId, timestamp);
                }
            }
        });
    }

    /**
     * Registers an event to the server
     *
     * @param sensorId  the sensor that was triggered
     * @param timestamp the timestamp for when it was triggered
     */
    private void registerEvent(SensorId sensorId, long timestamp) {
        try {
            Call<Void> call = droidRaceApiService.registerSensorEvent(sensorId, timestamp);
            Response<Void> response = call.execute();

            if (!response.isSuccessful()) {
                log.error("Couldn't register sensor data for " + sensorId + ", got: " + response.code() + " - " + response.message());
            }
        } catch (IOException e) {
            log.error("Couldn't sensor data from pi-sensor to server", e);
        }
    }

    /**
     * Returns true if the event should trigger a status change event to the server
     *
     * @param event    the sensor event for a GPIO port
     * @param sensorId the ID of the sensor that trigged
     * @return true if the event should be notified
     */
    private boolean shouldSendEvent(GpioPinDigitalStateChangeEvent event, SensorId sensorId) {
        return (sensorId.shouldTriggerOnHigh() && event.getState() == PinState.HIGH) || (!sensorId.shouldTriggerOnHigh() && event.getState() == PinState.LOW);
    }

    /**
     * Listens for context refresh event so that listeners for GPIO:s are registered when the application is
     * started.
     *
     * @param event the event for when the context is refreshed
     */
    @EventListener
    public void handleContextRefresh(ContextRefreshedEvent event) {
        try {
            for (SensorId sensorId : SensorId.values()) {
                registerListenerForSensor(sensorId);
                log.info("Listening for event " + sensorId.name() + " on pin " + sensorId.getPin().getName() + ", trigger when HIGH=" + sensorId.shouldTriggerOnHigh());
            }
        } catch (UnsatisfiedLinkError e) {
            log.error("Couldn't register GPIO:s for sensors, are we not running on a pi?");
        }
    }
}