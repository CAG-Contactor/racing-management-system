package se.cag.labs.raceadmin;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import se.cag.labs.raceadmin.services.LeaderBoardService;
import se.cag.labs.raceadmin.services.ResultType;
import se.cag.labs.raceadmin.services.UserResult;
import se.cag.labs.usermanager.User;

import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

@RestController
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class RaceAdministratorController {
    @Autowired
    private RaceRepository repository;

    @Autowired
    private LeaderBoardService leaderBoardService;

    @RequestMapping(value = "/registerforrace", method = RequestMethod.POST)
    public void registerForRace(@RequestBody User user) {
        Queue<RaceStatus> sfsd = new ArrayBlockingQueue<RaceStatus>(10);
        sfsd.addAll(repository.findAll());
        sfsd.add(new RaceStatus(user, RaceStatus.RaceState.QUEUEING));
        repository.save(sfsd);
        // TODO: Implement
    }

    @RequestMapping(value = "/onracestatusupdate", method = RequestMethod.POST)
    @ApiOperation(
            value = "Updates the status for a user"
    )
    public void onRaceStatusUpdate(@RequestBody RaceStatus status) {
        // TODO kontrollera att användaren finns och är aktiv
        // TODO ersätt med en builder?
        UserResult userResult = new UserResult();
        userResult.setUser(status.getUser());
        userResult.setTime(status.getFinishTime() - status.getStartTime());
        userResult.setMiddleTime(status.getMiddleTime());

        if (status.getState() == RaceStatus.RaceState.FINISH) {
            userResult.setResult(ResultType.FINISHED);
        } else if (status.getState() == RaceStatus.RaceState.TIME_OUT_NOT_STARTED) {
            userResult.setResult(ResultType.WALKOVER);
        } else {
            userResult.setResult(ResultType.DISQUALIFIED);
        }

        leaderBoardService.newResult(userResult);
    }
}
