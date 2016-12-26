package se.cag.labs.leaderboard;


import io.swagger.annotations.*;
import lombok.extern.log4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.Comparator.comparingLong;
import static java.util.Objects.nonNull;

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
    log.debug("GET /results");
    return repository.findAll().stream()
        .filter(Objects::nonNull)
        .filter(r -> Objects.equals(r.getResult(), ResultType.FINISHED))
        .sorted(comparingLong(UserResult::getTime))
        .collect(Collectors.toList());
  }

  @RequestMapping(value = "/resultsBy", method = RequestMethod.POST)
  @ApiOperation(
          value = "Returns the races for a given user",
          notes = "This will return races sorted on fastest result time first.",
          response = UserResult.class,
          responseContainer = "List")
  @ApiResponses(value = {
          @ApiResponse(code = 200, message = "Races for a user returned"),
  })
  public List<UserResult> resultsBy(@RequestBody User user) {
    log.debug("GET /resultsBy:" + user);
    return nonNull(user) ? repository.findAll().stream()
            .filter(r -> r.getUser().getUserId().equals(user.getUserId()))
            .sorted((r1, r2) -> Long.compare(r1.getTime(), r2.getTime()))
            .collect(Collectors.toList()) : Collections.emptyList();
  }

  @RequestMapping(value = "/results/{id}", method = RequestMethod.DELETE)
  @ApiOperation(value = "Deletes the race with a given id")
  @ApiResponses(value = {
          @ApiResponse(code = 200, message = "Race deleted"),
  })
  public ResponseEntity deleteRace(@PathVariable("id") String id) {
    log.debug("DELETE /results/:" + id);
    repository.delete(id);

    return new ResponseEntity(HttpStatus.OK);
  }
}
