package se.cag.labs.leaderboard;


import io.swagger.annotations.*;
import lombok.extern.log4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Log4j
@Api(basePath = "*",
        value = "Leaderboard",
        description = "This service keeps track of the results from the races."
)
@RestController
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class LeaderBoardController {
    @Autowired
    private LeaderBoardRepository repository;

    @RequestMapping(value = "/results", method = RequestMethod.POST)
    @ApiOperation(
            value = "Saves a new result to the leaderboard",
            notes = "Saves a new result to the leaderboard.")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "The results where saved successfully"),
    })
    public void newResult(@RequestBody UserResult userResult) {
        log.debug("POST /results:" + userResult);
        repository.insert(userResult);
    }

    @RequestMapping(value = "/results", method = RequestMethod.GET)
    @ApiOperation(
            value = "Returns the leaderboard",
            notes = "This will return the leaderboard sorted on fastest result time first. " +
                    "All results that doesn't have the result status FINISHED are sorted last.",
            response = UserResult.class,
            responseContainer = "List")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "The leaderboard is returned"),
    })
    public List<UserResult> results() {
        // TODO sort on time
        log.debug("GET /results");
        return repository.findAll();
    }
}
