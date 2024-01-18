package se.cag.labs.leaderboard;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static java.util.Comparator.comparingLong;
import static java.util.Objects.nonNull;

@Slf4j
@RestController
@CrossOrigin(origins = { "http://localhost:3000" }, methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE}, allowCredentials = "true")
public class LeaderBoardController {
  @Autowired
  private LeaderBoardRepository repository;

  @RequestMapping(value = "/results", method = RequestMethod.POST)
  public void newResult(@RequestBody UserResult userResult) {
    log.debug("POST /results:" + userResult);
    repository.insert(userResult);
  }

  @RequestMapping(value = "/results", method = RequestMethod.GET)
  public List<UserResult> results() {
    log.debug("GET /results");
    return repository.findAll().stream()
        .filter(Objects::nonNull)
        .filter(r -> Objects.equals(r.getResult(), ResultType.FINISHED))
        .sorted(comparingLong(UserResult::getTime))
        .collect(Collectors.toList());
  }

  @RequestMapping(value = "/resultsBy", method = RequestMethod.POST)
  public List<UserResult> resultsBy(@RequestBody User user) {
    log.debug("GET /resultsBy:" + user);
    return nonNull(user) ? repository.findAll().stream()
            .filter(r -> r.getUser().getUserId().equals(user.getUserId()))
            .sorted((r1, r2) -> Long.compare(r1.getTime(), r2.getTime()))
            .collect(Collectors.toList()) : Collections.emptyList();
  }

  @RequestMapping(value = "/results/{id}", method = RequestMethod.DELETE)
  public ResponseEntity deleteRace(@PathVariable("id") String id) {
    log.debug("DELETE /results/:" + id);
    repository.deleteById(id);

    return new ResponseEntity(HttpStatus.OK);
  }

  @RequestMapping(value = "/ping", method = RequestMethod.GET)
  public ResponseEntity ping() {
    log.debug("/ping");

    return new ResponseEntity(HttpStatus.OK);
  }
}
