package se.cag.labs.pisensor;

import com.pi4j.io.gpio.*;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;
import org.springframework.beans.factory.annotation.Value;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**
 * Sensor listening for events from the the race sensors and registers them to the backend.
 */
public class TimerSensor {

    private static long startTime;
    private static long splitTime;
    private static long finishTime;

    @Value("${server.raceadministrator.base.uri}")
    private static String baseUri;

    public static void main(String[] args) throws InterruptedException {
        System.out.println("GPIO TimerSensor ... started.");

        final GpioController gpio = GpioFactory.getInstance();
        final GpioPinDigitalInput startSensor = gpio.provisionDigitalInputPin(RaspiPin.GPIO_00);
        final GpioPinDigitalInput splitSensor = gpio.provisionDigitalInputPin(RaspiPin.GPIO_02);
        final GpioPinDigitalInput finishSensor = gpio.provisionDigitalInputPin(RaspiPin.GPIO_03);


        System.out.println(" ... PRESS <CTRL-C> TO STOP THE PROGRAM.");

        startSensor.addListener(new GpioPinListenerDigital() {
            @Override
            public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
                if (event.getState() == PinState.HIGH) {
                    startTime = System.currentTimeMillis();

                    System.out.println(" --> CHANGE ON START SENSOR - TIME: 0" + startTime);
                    registerEvent("START", String.valueOf(startTime));
                }
            }
        });

        splitSensor.addListener(new GpioPinListenerDigital() {
            @Override
            public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
                if (event.getState() == PinState.HIGH) {
                    splitTime = System.currentTimeMillis();
                    System.out.println(" --> CHANGE ON SPLIT SENSOR - TIME: " + splitTime);
                    registerEvent("SPLIT", String.valueOf(splitTime));
                }
            }
        });

        finishSensor.addListener(new GpioPinListenerDigital() {
            @Override
            public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
                if (event.getState() == PinState.HIGH) {
                    finishTime = System.currentTimeMillis();
                    System.out.println(" --> CHANGE ON FINISH SENSOR - TIME: " + finishTime);
                    registerEvent("FINISH", String.valueOf(finishTime));
                }
            }
        });

        for (; ; ) {
            Thread.sleep(500);
        }
    }

    private static void registerEvent(String sensor, String time) {
        try {
            URL url = new URL("http://54.165.222.28:80/passageDetected?sensorID=" + sensor + "&timestamp=" + time);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");

            System.out.println("RESPONSE FROM SERVER: " + conn.getResponseCode());

            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

            String output;
            while ((output = br.readLine()) != null) {
                System.out.println(output);
            }

            conn.disconnect();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
