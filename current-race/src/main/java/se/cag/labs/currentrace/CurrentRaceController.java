package se.cag.labs.currentrace;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class CurrentRaceController {

    @RequestMapping("/startRace")
    public void startRace(@RequestParam String callbackUrl) {
        System.out.println("Starting race: " + callbackUrl);
    }
}
