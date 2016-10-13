package se.cag.labs.currentrace.services;

import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.cag.labs.currentrace.services.repository.datamodel.CurrentRaceStatus;
import se.cag.labs.currentrace.timer.VerifyRacePassagesTimerTask;

import javax.annotation.PostConstruct;
import java.util.Timer;
import java.util.TimerTask;

@Service
@Log4j
public class TimerService {

  @Autowired
  private VerifyRacePassagesTimerTask raceTimerTask;

  private Timer timer;

  @PostConstruct
  public void init() {
    startTimer();
  }

  public void startTimer() {
    log.info("Start timer.");
    if (timer == null) {
      timer = new Timer("RacingTimer");
      timer.schedule(raceTimerTask, 1000, VerifyRacePassagesTimerTask.TIME_INTERVAL);
      log.info("Timer started");
    }
  }

  public void stopTimer() {
    timer.cancel();
  }

  public void trigAsyncStatusUpdate() {
    new Timer("Oneshot").schedule(
      new TimerTask() {
        @Override
        public void run() {
          log.info("Trig status update");
          raceTimerTask.trigUpdate(CurrentRaceStatus.builder().build());
        }
      },
      50);
  }
}
