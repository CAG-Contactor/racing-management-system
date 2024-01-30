package se.cag.labs.pisensor.component;

import com.pi4j.Pi4J;
import com.pi4j.context.Context;
import com.pi4j.io.gpio.digital.DigitalInput;
import com.pi4j.io.gpio.digital.DigitalState;
import com.pi4j.io.gpio.digital.DigitalStateChangeEvent;
import com.pi4j.io.gpio.digital.PullResistance;
import jakarta.annotation.PreDestroy;
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
    private final Context pi4j;

    @Autowired
    public RegisterSensorsComponent(DroidRaceApiService droidRaceApiService) {
        this.droidRaceApiService = droidRaceApiService;
        this.pi4j = Pi4J.newAutoContext();
    }

    @PreDestroy
    public void close() {
        this.pi4j.shutdown();
    }
    /**
     * Adds ourself as a listener for a GPIO port
     *
     * @param sensorId the sensor to listen to
     */
    private void registerListenerForSensor(final SensorId sensorId) {

        var buttonConfig = DigitalInput.newConfigBuilder(pi4j)
                .id(sensorId.toString())
                .name(sensorId.toString())
                .address(sensorId.getPin())
                .pull(PullResistance.PULL_DOWN)
                .provider("pigpio-digital-input");
        var button = pi4j.create(buttonConfig);
        button.addListener(event -> {
            if (shouldSendEvent(event, sensorId)) {
                long timestamp = System.currentTimeMillis();
                log.info(" --> CHANGE ON " + sensorId + " - TIME: " + timestamp);
                registerEvent(sensorId, timestamp);
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
    private boolean shouldSendEvent(DigitalStateChangeEvent event, SensorId sensorId) {
        return (sensorId.shouldTriggerOnHigh() && event.state() == DigitalState.HIGH)
                || (!sensorId.shouldTriggerOnHigh() && event.state() == DigitalState.LOW);
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
                log.info("Listening for event " + sensorId.name() + " on BCM pin " + sensorId.getPin() + ", trigger when HIGH=" + sensorId.shouldTriggerOnHigh());
            }
        } catch (UnsatisfiedLinkError e) {
            log.error("Couldn't register GPIO:s for sensors, are we not running on a pi?");
        }
    }
}