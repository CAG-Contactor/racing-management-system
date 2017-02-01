package se.cag.labs.pisensor;

import lombok.extern.log4j.Log4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import se.cag.labs.pisensor.utils.KeyStoreUtils;

import java.io.IOException;

/**
 * Sensor listening for events from the the race sensors and registers them to the backend.
 */
@SpringBootApplication
@EnableScheduling
@Log4j
public class PiSensor {
    public static void main(String[] args) throws InterruptedException, IOException {

        // Download certificates and store them to a keystore
        KeyStoreUtils.updateKeyStore("sumorace.caglabs.se", 443, "/tmp/keystore.jks", "changeme");
        System.setProperty("javax.net.ssl.trustStore", "/tmp/keystore.jks");

        SpringApplication.run(PiSensor.class, args);
    }
}
