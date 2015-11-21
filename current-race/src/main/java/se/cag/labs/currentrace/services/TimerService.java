package se.cag.labs.currentrace.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.cag.labs.currentrace.timer.VerifyRacePassagesTimerTask;

import java.util.Timer;
import java.util.TimerTask;

@Service
public class TimerService {

    @Autowired
    private TimerTask raceTimerTask;


    private Timer timer;

    private  String message;


    public void startTimer() {
        System.out.println("Start timer.");
        if (timer == null) {
            timer = new Timer("RacingTimer");
            timer.schedule(raceTimerTask, 1000, VerifyRacePassagesTimerTask.TIME_INTERVAL);
            System.out.println("Timer started");
        }
    }

    public void stopTimer() {
        timer.cancel();
    }



}
