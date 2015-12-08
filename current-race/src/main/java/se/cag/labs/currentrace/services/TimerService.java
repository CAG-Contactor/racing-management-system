package se.cag.labs.currentrace.services;

import lombok.extern.log4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import se.cag.labs.currentrace.timer.*;

import java.util.*;

@Service
@Log4j
public class TimerService {

    @Autowired
    private TimerTask raceTimerTask;

    private Timer timer;

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


}
