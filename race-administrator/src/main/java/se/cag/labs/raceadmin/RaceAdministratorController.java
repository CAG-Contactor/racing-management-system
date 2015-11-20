package se.cag.labs.raceadmin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import se.cag.labs.usermanager.User;

import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;

@RestController
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class RaceAdministratorController {
    @Autowired
    private RaceRepository repository;

    @RequestMapping(value = "/registerforrace", method = RequestMethod.POST)
    public void registerForRace(@RequestBody User user) {
        Queue<RaceStatus> sfsd = new ArrayBlockingQueue<RaceStatus>(10);
        sfsd.addAll(repository.findAll());
        sfsd.add(new RaceStatus(user, RaceStatus.RaceState.QUEUEING));
        repository.save(sfsd);
        // TODO: Implement
    }

    @RequestMapping("/onracestatusupdate")
    public void onRaceStatusUpdate(RaceStatus status) {
        // TODO: Implement
    }
}
