package se.cag.labs.raceadmin;

import io.swagger.annotations.*;
import lombok.extern.log4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.web.bind.annotation.*;
import se.cag.labs.raceadmin.services.*;
import se.cag.labs.usermanager.*;

import java.util.*;
import java.util.concurrent.*;

@Log4j
@RestController
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class RaceAdministratorController {
    @Autowired
    private RaceRepository repository;

    @Autowired
    private LeaderBoardService leaderBoardService;

    @RequestMapping(value = "/userqueue", method = RequestMethod.POST)
    public void registerForRace(@RequestBody User user) {
        log.debug("POST /userqueue:" + user);
        Queue<User> sfsd = new ArrayBlockingQueue<>(10);
        sfsd.addAll(repository.findAll());
        sfsd.add(user);
        repository.save(sfsd);
        // TODO: Implement
    }

    @RequestMapping(value = "/userqueue", method = RequestMethod.GET)
    public void getQueue() {
        log.debug("GET /userqueue");
        // TODO: Implement
    }

    @RequestMapping(value = "/onracestatusupdate", method = RequestMethod.POST)
    @ApiOperation(value = "Updates the status for a user")
    public void onRaceStatusUpdate(@RequestBody RaceStatus status) {
        // TODO kontrollera att användaren finns och är aktiv
        // TODO ersätt med en builder?
        log.debug("onracestatusupdate:" + status);
        if (status.getState() == RaceStatus.RaceState.INACTIVE) {
            UserResult userResult = new UserResult();
            if (status.getEvent() == RaceStatus.RaceEvent.FINISH) {
                userResult.setUser(status.getUser());
                userResult.setTime(status.getFinishTime() - status.getStartTime());
                userResult.setMiddleTime(status.getMiddleTime() - status.getStartTime());
                userResult.setResult(ResultType.FINISHED);
            } else if (status.getEvent() == RaceStatus.RaceEvent.TIME_OUT_NOT_STARTED) {
                userResult.setResult(ResultType.WALKOVER);
            } else {
                userResult.setResult(ResultType.DISQUALIFIED);
            }

            leaderBoardService.newResult(userResult);
        }
    }
}
