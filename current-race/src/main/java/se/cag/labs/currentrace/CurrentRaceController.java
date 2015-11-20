package se.cag.labs.currentrace;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*", methods = {RequestMethod.GET})
public class CurrentRaceController {

    @RequestMapping("/startRace")
    public String startRace(@RequestParam String callbackUrl) {
        return "Starting race: " + callbackUrl;
    }

    @RequestMapping("/cancelRace")
    public String cancelRace() {
        return "Cancelling race";
    }

    @RequestMapping("/passageDetected")
    public String passageDetected(@RequestParam String sensorID, @RequestParam long timestamp) {
        return "passageDetected on : " + sensorID + " at: " + timestamp;
    }

    @RequestMapping("/status")
    public String status() {
        return "Status";
    }
}
