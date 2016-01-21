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
  }

  @RequestMapping(value = "/on-race-status-update", method = RequestMethod.POST)
  @ApiOperation(value = "Handle status updates for the current race.")
  public void onRaceStatusUpdate(@RequestBody RaceStatus status) {
    // TODO kontrollera att användaren finns och är aktiv
    // TODO ersätt med en builder?
    log.debug("on-race-status-update:" + status);
    Optional<RaceStatus> activeRace = activeRaceRepository.findAll().stream().findFirst();
    if (activeRace.isPresent()) {
      clientApiService.sendEvent(ClientApiService.Event.builder().data(activeRace.get()).build());
      if (status.getState() == RaceStatus.RaceState.INACTIVE) {
        UserResult userResult = new UserResult();
        userResult.setUser(activeRace.get().getUser());
        if (status.getEvent() == RaceStatus.RaceEvent.FINISH) {
          userResult.setTime(status.getFinishTime() - status.getStartTime());
          userResult.setSplitTime(status.getSplitTime() - status.getStartTime());
          userResult.setResult(UserResult.ResultType.FINISHED);
        } else if (status.getEvent() == RaceStatus.RaceEvent.TIME_OUT_NOT_STARTED) {
          userResult.setResult(UserResult.ResultType.WALKOVER);
        } else {
          userResult.setResult(UserResult.ResultType.DISQUALIFIED);
        }
        clientApiService.sendEvent(ClientApiService.Event.builder().data(userResult).build());

        leaderBoardService.newResult(userResult);
        activeRaceRepository.delete(activeRace.get().getId());
        startNextRace();
      } else {
        activeRace.get().setEvent(status.getEvent());
        activeRace.get().setState(status.getState());
        activeRace.get().setStartTime(status.getStartTime());
        activeRace.get().setSplitTime(status.getSplitTime());
        activeRace.get().setFinishTime(status.getFinishTime());
        activeRaceRepository.save(activeRace.get());
      }
    }
  }

  private void startNextRace() {
    User user = sortedQueue().poll();
    if (user != null) {
      activeRaceRepository.save(new RaceStatus(user));
      currentRaceService.startRace();
      userQueueRepository.delete(user.getId());
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
