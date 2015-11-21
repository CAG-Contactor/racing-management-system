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
        if (isActiveAndConsistent(raceStatus)) {
            long raceActivatedTime = raceStatus.getRaceActivatedTime();
            RaceStatus.Event eventStatus = RaceStatus.Event.NONE;
            if (raceStatus.getStartTime() == null) {
                if (raceActivatedTime - raceStatus.getRaceActivatedTime() >= TIME_LIMIT) {
                    eventStatus = RaceStatus.Event.TIME_OUT_NOT_STARTED;
                }
            }
            if (raceStatus.getMiddleTime() == null && raceStatus.getStartTime() != null)  {
                if (raceStatus.getStartTime() - raceStatus.getMiddleTime() >= TIME_LIMIT) {
                    eventStatus = RaceStatus.Event.DISQUALIFIED;
                }
            }
            if (raceStatus.getFinishTime() == null && raceStatus.getMiddleTime() != null) {
                if (raceStatus.getMiddleTime() - raceStatus.getFinishTime() >= TIME_LIMIT) {
                    eventStatus = RaceStatus.Event.TIME_OUT_NOT_FINISHED;
                }
            }
            System.out.println("raceStatus: " + raceStatus);
            repository.save(raceStatus);
        }
    }

    private boolean isActiveAndConsistent(RaceStatus raceStatus) {
        return raceStatus != null && raceStatus.getRaceActivatedTime() != null && raceStatus.getState() == RaceStatus.State.ACTIVE;
    }


 }
