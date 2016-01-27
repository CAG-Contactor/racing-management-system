package se.cag.labs.raceadmin;

import io.swagger.annotations.*;
import lombok.extern.log4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.*;
import se.cag.labs.raceadmin.peerservices.*;

import java.util.*;

import static java.util.stream.Collectors.*;

@Log4j
@RestController
@Api(basePath = "*",
  value = "Race Administrator",
  description = "This service administrates the races.",
  produces = "application/json")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class RaceAdministratorController {
  @Autowired
  private UserQueueRepository userQueueRepository;
  @Autowired
  private ActiveRaceRepository activeRaceRepository;
  @Autowired
  private LeaderBoardService leaderBoardService;
  @Autowired
  private CurrentRaceService currentRaceService;
  @Autowired
  private ClientApiService clientApiService;

  private RestTemplate restTemplate = new RestTemplate();

  @RequestMapping(value = "/userqueue", method = RequestMethod.POST)
  @ApiOperation(value = "Registers a user as a competitor in a race.")
  @ApiResponses(value = {
    @ApiResponse(code = 201, message = "User is registered for race.")
  })
  public void registerForRace(@RequestBody User user) {
    log.debug("POST /userqueue:" + user);
    user.setTimestamp(System.currentTimeMillis());
    boolean userIsTheActiveRace = activeRaceRepository.findAll().stream()
      .map(RaceStatus::getUser)
      .filter(u -> u != null)
      .filter(u -> Objects.equals(u.getUserId(), user.getUserId()))
      .findFirst()
      .isPresent();
    boolean userIsAlreadyEnqueued = userQueueRepository.findUserByUserId(user.getUserId()) != null;
    if (userIsAlreadyEnqueued || userIsTheActiveRace) {
      return;
    }
    userQueueRepository.save(user);

    clientApiService.sendEvent(ClientApiService.Event.builder().eventType("QUEUE_UPDATED").data(user).build());

    if (userQueueRepository.count() == 1 && activeRaceRepository.count() == 0) {
      startNextRace();
    }
  }

  @RequestMapping(value = "/userqueue", method = RequestMethod.GET)
  @ApiOperation(value = "Get the contents of the queue.")
  @ApiResponses(value = {
    @ApiResponse(code = 200, message = "The queued users.")
  })
  public Queue<User> getQueue() {
    log.debug("GET /userqueue");
    return sortedQueue();
  }

  @RequestMapping(value = "/userqueue", method = RequestMethod.DELETE)
  @ApiOperation(value = "Unegisters a user as a competitor in a race.")
  @ApiResponses(value = {
    @ApiResponse(code = 200, message = "User is unregistered for race.")
  })
  public void unregisterForRace(@RequestBody User user) {
    log.debug("DELETE /userqueue:" + user);
    final User existingUser = userQueueRepository.findUserByUserId(user.getUserId());
    userQueueRepository.delete(existingUser.getId());
    clientApiService.sendEvent(ClientApiService.Event.builder().eventType("QUEUE_UPDATED").data(user).build());
  }

  @RequestMapping(value="/currentrace")
  @ApiOperation(value = "Gets the status of the active race")
  public RaceStatus getCurrentRaceStatus() {
    RaceStatus currentRaceStatus = currentRaceService.status();
    Optional<RaceStatus> maybeActiveRace = activeRaceRepository.findAll().stream().findFirst();
    return maybeActiveRace
      .map(rs -> {rs.setCurrentTime(currentRaceStatus.getCurrentTime());return rs;})
      .orElse(new RaceStatus(null));
  }

  @RequestMapping(value = "/on-race-status-update", method = RequestMethod.POST)
  @ApiOperation(value = "Handle status updates for the current race.")
  public void onRaceStatusUpdate(@RequestBody RaceStatus status) {
    // TODO kontrollera att användaren finns och är aktiv
    // TODO ersätt med en builder?
    log.debug("on-race-status-update:" + status);
    Optional<RaceStatus> maybeActiveRace = activeRaceRepository.findAll().stream().findFirst();
    if (maybeActiveRace.isPresent()) {
      RaceStatus activeRaceStatus = maybeActiveRace.get();
      status.setUser(activeRaceStatus.getUser());
      clientApiService.sendEvent(ClientApiService.Event.builder().eventType("CURRENT_RACE_STATUS").data(status).build());
      if (status.getState() == RaceStatus.RaceState.INACTIVE) {
        UserResult userResult = new UserResult();
        userResult.setUser(activeRaceStatus.getUser());
        if (status.getEvent() == RaceStatus.RaceEvent.FINISH) {
          userResult.setTime(status.getFinishTime() - status.getStartTime());
          userResult.setSplitTime(status.getSplitTime() - status.getStartTime());
          userResult.setResult(UserResult.ResultType.FINISHED);
        } else if (status.getEvent() == RaceStatus.RaceEvent.TIME_OUT_NOT_STARTED) {
          userResult.setResult(UserResult.ResultType.WALKOVER);
        } else {
          userResult.setResult(UserResult.ResultType.DISQUALIFIED);
        }
        clientApiService.sendEvent(ClientApiService.Event.builder().eventType("NEW_RESULT").data(userResult).build());

        leaderBoardService.newResult(userResult);
        activeRaceRepository.delete(activeRaceStatus.getId());
        startNextRace();
      } else {
        activeRaceStatus.setEvent(status.getEvent());
        activeRaceStatus.setState(status.getState());
        activeRaceStatus.setStartTime(status.getStartTime());
        activeRaceStatus.setSplitTime(status.getSplitTime());
        activeRaceStatus.setFinishTime(status.getFinishTime());
        activeRaceRepository.save(activeRaceStatus);
      }
    }
  }

  @RequestMapping(value = "/reset-race", method = RequestMethod.POST)
  @ApiOperation(value = "Handle status updates for the current race.")
  public void resetRace() {
    currentRaceService.cancelRace();
  }

  private void startNextRace() {
    User user = sortedQueue().poll();
    if (user != null) {
      activeRaceRepository.save(new RaceStatus(user));
      currentRaceService.startRace();
      userQueueRepository.delete(user.getId());
      clientApiService.sendEvent(ClientApiService.Event.builder().eventType("QUEUE_UPDATED").data(user).build());
    }
  }

  private Queue<User> sortedQueue() {
    return new ArrayDeque<>(
      userQueueRepository.findAll().stream()
        .sorted((a, b) -> Long.compare(a.getTimestamp(), b.getTimestamp()))
        .collect(toList())
    );
  }
}
