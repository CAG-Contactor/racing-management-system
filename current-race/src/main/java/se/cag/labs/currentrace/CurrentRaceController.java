package se.cag.labs.currentrace;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import se.cag.labs.currentrace.services.CancelRaceService;
import se.cag.labs.currentrace.services.PassageDetectedService;
import se.cag.labs.currentrace.services.StartRaceService;
import se.cag.labs.currentrace.services.StatusService;

@RestController
@CrossOrigin(origins = "*", methods = {RequestMethod.GET})
public class CurrentRaceController {
    @Autowired
    private CurrentRaceRepository repository;
    @Autowired
    private StartRaceService startRaceService;
    @Autowired
    private CancelRaceService cancelRaceService;
    @Autowired
    private PassageDetectedService passageDetectedService;
    @Autowired
    private StatusService statusService;

    @RequestMapping("/startRace")
    public String startRace(@RequestParam String callbackUrl) {
        return startRaceService.startRace(callbackUrl);
    }

    @RequestMapping("/cancelRace")
    public void cancelRace() {
        cancelRaceService.cancelRace();
    }

    @RequestMapping("/passageDetected")
    public String passageDetected(@RequestParam String sensorID, @RequestParam long timestamp) {
        return passageDetectedService.passageDetected(sensorID, timestamp);
    }

    @RequestMapping("/status")
    public RaceStatus status() {
        return statusService.status();
    }
}
