package se.cag.labs.raceadmin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import se.cag.labs.usermanager.User;

import java.util.*;

@RestController
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class RaceAdministratorController {
    @Autowired
    private RaceRepository repository;

    @RequestMapping("/registerforrace")
    public void registerForRace(User user) {
        // TODO: Implement
    }

    @RequestMapping("/onracestatusupdate")
    public void onRaceStatusUpdate(RaceStatus status) {
        // TODO: Implement
    }
}
