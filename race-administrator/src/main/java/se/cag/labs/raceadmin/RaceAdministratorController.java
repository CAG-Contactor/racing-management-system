package se.cag.labs.raceadmin;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import se.cag.labs.raceadmin.peerservices.ClientApiService;
import se.cag.labs.raceadmin.peerservices.CurrentRaceService;
import se.cag.labs.raceadmin.peerservices.Event;
import se.cag.labs.raceadmin.peerservices.LeaderBoardService;

import java.util.ArrayDeque;
import java.util.Objects;
import java.util.Optional;
import java.util.Queue;

import static java.util.stream.Collectors.toList;

@Slf4j
@RestController
@CrossOrigin(origins = { "http://localhost:3000", "http://192.168.0.50:30080" }, methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE}, allowCredentials = "true")
public class RaceAdministratorController {
  @Autowired
  private UserQueueRepository userQueueRepository;
  @Autowired
  private ActiveRaceRepository activeRaceRepository;
  @Autowired
  private LastRaceRepository lastRaceRepository;
  @Autowired
  private LeaderBoardService leaderBoardService;
  @Autowired
  private CurrentRaceService currentRaceService;
  @Autowired
  private ClientApiService clientApiService;

  private RestTemplate restTemplate = new RestTemplate();

  @RequestMapping(value = "/userqueue", method = RequestMethod.POST)
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

    clientApiService.sendEvent(Event.builder().eventType("QUEUE_UPDATED").data(user).build());

    if (userQueueRepository.count() == 1 && activeRaceRepository.count() == 0) {
      startNextRace();
    }
  }

  @RequestMapping(value = "/userqueue", method = RequestMethod.GET)
  public Queue<User> getQueue() {
    log.debug("GET /userqueue");
    return sortedQueue();
  }

  @RequestMapping(value = "/userqueue", method = RequestMethod.DELETE)
  public void unregisterForRace(@RequestBody User user) {
    log.debug("DELETE /userqueue:" + user);
    final User existingUser = userQueueRepository.findUserByUserId(user.getUserId());
    userQueueRepository.delete(existingUser);
    clientApiService.sendEvent(Event.builder().eventType("QUEUE_UPDATED").data(user).build());
  }

  @RequestMapping(value="/currentrace")
  public RaceStatus getCurrentRaceStatus() {
    RaceStatus currentRaceStatus = currentRaceService.status();
    Optional<RaceStatus> maybeActiveRace = activeRaceRepository.findAll().stream().findFirst();
    return maybeActiveRace
      .map(rs -> {rs.setCurrentTime(currentRaceStatus.getCurrentTime());return rs;})
      .orElse(new RaceStatus(null));
  }

  @RequestMapping(value="/lastrace")
  public RaceStatus getLastRaceStatus() {
    Optional<LastRaceStatus> maybeLastRace = lastRaceRepository.findAll().stream().findFirst();
    return (RaceStatus) maybeLastRace.orElse(new RaceStatus(null));
  }

  @RequestMapping(value = "/on-race-status-update", method = RequestMethod.POST)
  public void onRaceStatusUpdate(@RequestBody RaceStatus status) {
    // TODO kontrollera att användaren finns och är aktiv
    // TODO ersätt med en builder?
    log.debug("on-race-status-update:" + status);
    Optional<RaceStatus> maybeActiveRace = activeRaceRepository.findAll().stream().findFirst();
    if (maybeActiveRace.isPresent()) {
      RaceStatus activeRaceStatus = maybeActiveRace.get();
      status.setUser(activeRaceStatus.getUser());
      clientApiService.sendEvent(Event.builder().eventType("CURRENT_RACE_STATUS").data(status).build());
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
        clientApiService.sendEvent(Event.builder().eventType("NEW_RESULT").data(userResult).build());
        leaderBoardService.newResult(userResult);
        lastRaceRepository.deleteAll();
        lastRaceRepository.save(status);

        activeRaceRepository.delete(activeRaceStatus);
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
  public void resetRace() {
    log.debug("Canceling current race....");
    currentRaceService.cancelRace();
  }

  private void startNextRace() {
    User user = sortedQueue().poll();
    if (user != null) {
      activeRaceRepository.save(new RaceStatus(user));
      currentRaceService.startRace();
      userQueueRepository.delete(user);
      clientApiService.sendEvent(Event.builder().eventType("QUEUE_UPDATED").data(user).build());
    }
  }

  @RequestMapping(path = "/ping", method = RequestMethod.GET)
  public ResponseEntity ping() {

    return new ResponseEntity(HttpStatus.OK);
  }

  private Queue<User> sortedQueue() {
    return new ArrayDeque<>(
      userQueueRepository.findAll().stream()
        .sorted((a, b) -> Long.compare(a.getTimestamp(), b.getTimestamp()))
        .collect(toList())
    );
  }
}
