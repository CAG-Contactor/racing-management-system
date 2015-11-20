package se.cag.labs.currentrace;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@CrossOrigin(origins = "*", methods = {RequestMethod.GET})
public class CurrentRaceController {
    @Autowired
    private CurrentRaceRepository repository;

    @RequestMapping("/startRace")
    public String startRace(@RequestParam String callbackUrl) {
        RaceStatus activeRaceStatus = repository.findByState(RaceStatus.State.ACTIVE);

        if(activeRaceStatus == null) {
            repository.save(new RaceStatus(RaceStatus.Event.START, new Date(), null, null, RaceStatus.State.ACTIVE));
            return "Starting race: " + callbackUrl;
        } else {
            return "Race is already started";
        }
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
    public RaceStatus status() {
        return repository.findByRaceId(RaceStatus.ID);
    }
}
