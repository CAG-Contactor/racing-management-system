package se.cag.labs.currentrace.services;

import lombok.extern.log4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import se.cag.labs.currentrace.services.repository.datamodel.*;
import se.cag.labs.currentrace.timer.*;

import javax.annotation.*;
import java.util.*;

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
