package se.cag.labs.currentrace.timer;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import se.cag.labs.currentrace.services.repository.CurrentRaceRepository;
import se.cag.labs.currentrace.services.repository.datamodel.RaceStatus;

import java.util.TimerTask;

@Component
public class VerifyRacePassagesTimerTask extends TimerTask {

    public static final long TIME_INTERVAL = 10 * 1000;
    public static final long TIME_LIMIT = 3 * 60 * 1000;

    @Autowired
    private CurrentRaceRepository repository;

    @Override
    public void run() {
        RaceStatus raceStatus = repository.findByRaceId(RaceStatus.ID);
        System.out.println("raceStatus = " + raceStatus);
        if (raceStatus != null && raceStatus.getState() == RaceStatus.State.ACTIVE) {
            long currentTime = System.currentTimeMillis();
            RaceStatus.Event eventStatus = RaceStatus.Event.NONE;
            if (currentTime - raceStatus.getRaceActivatedTime() >= TIME_LIMIT && raceStatus.getStartTime() == null) {
                eventStatus = RaceStatus.Event.TIME_OUT_NOT_STARTED;
            }

            if (raceStatus.getStartTime() != null) {
                if (currentTime - raceStatus.getStartTime() >= TIME_LIMIT) {
                    eventStatus = RaceStatus.Event.TIME_OUT_NOT_STARTED;
                } else {
                    eventStatus = RaceStatus.Event.START;
                }
            }
            if (raceStatus.getMiddleTime() != null) {
                if (currentTime - raceStatus.getMiddleTime() >= TIME_LIMIT) {
                    eventStatus = RaceStatus.Event.DISQUALIFIED;
                } else {
                    eventStatus = RaceStatus.Event.MIDDLE;
                }
            }
            if (raceStatus.getFinishTime() != null) {
                if (currentTime - raceStatus.getFinishTime() >= TIME_LIMIT) {
                    eventStatus = RaceStatus.Event.TIME_OUT_NOT_FINISHED;
                } else {
                    eventStatus = RaceStatus.Event.FINISH;
                }
            }
            System.out.println("raceStatus: " + raceStatus);
            repository.save(raceStatus);
        }
    }

 }
