package se.cag.labs.leaderboard;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(basePath = "*", value = "Leaderboard", description = "Leaderboard for all races", produces = "application/json")
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
        repository.insert(userResult);
    }

    @RequestMapping(value="/results", method = RequestMethod.GET)
    @ApiOperation(
            value = "Returns the leaderboard",
            notes = "This will return the leaderboard sorted on fastest result time first. All results that doesn't have the result status FINISHED are sorted last.",
            response = UserResult.class,
            responseContainer = "List")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "The leaderboard is returned"),
    })
    public List<UserResult> results() {
        // TODO sort on time
        return repository.findAll();
    }
}
