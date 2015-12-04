package se.cag.labs.currentrace.timer;


import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import se.cag.labs.currentrace.services.CallbackService;
import se.cag.labs.currentrace.services.repository.CurrentRaceRepository;
import se.cag.labs.currentrace.services.repository.datamodel.CurrentRaceStatus;

import java.util.TimerTask;

@Component
@Log
public class VerifyRacePassagesTimerTask extends TimerTask {

    public static final long TIME_INTERVAL = 10 * 1000;
    public static final long TIME_LIMIT = 3 * 60 * 1000;

    @Autowired
    private CurrentRaceRepository repository;

    @Autowired
    private CallbackService callbackService;

    @Override
    public void run() {
        CurrentRaceStatus currentRaceStatus = repository.findByRaceId(CurrentRaceStatus.ID);
        if (isActiveAndConsistent(currentRaceStatus)) {
            long currentTime = System.currentTimeMillis();
            if (currentRaceStatus.getStartTime() == null) {
                if (currentTime - currentRaceStatus.getRaceActivatedTime() >= TIME_LIMIT) {
                    log.fine(CurrentRaceStatus.Event.TIME_OUT_NOT_STARTED.name());
                    currentRaceStatus.setEvent(CurrentRaceStatus.Event.TIME_OUT_NOT_STARTED);
                    currentRaceStatus.setState(CurrentRaceStatus.State.INACTIVE);
                }
            }
            if (currentRaceStatus.getMiddleTime() == null && currentRaceStatus.getStartTime() != null) {
                if (currentTime - currentRaceStatus.getStartTime() >= TIME_LIMIT) {
                    log.fine(CurrentRaceStatus.Event.DISQUALIFIED.name());
                    currentRaceStatus.setEvent(CurrentRaceStatus.Event.DISQUALIFIED);
                    currentRaceStatus.setState(CurrentRaceStatus.State.INACTIVE);
                }
            }
            if (currentRaceStatus.getFinishTime() == null && currentRaceStatus.getMiddleTime() != null) {
                if (currentTime - currentRaceStatus.getMiddleTime() >= TIME_LIMIT) {
                    log.fine(CurrentRaceStatus.Event.TIME_OUT_NOT_FINISHED.name());
                    currentRaceStatus.setEvent(CurrentRaceStatus.Event.TIME_OUT_NOT_FINISHED);
                    currentRaceStatus.setState(CurrentRaceStatus.State.INACTIVE);
                }
            }
            log.info(currentRaceStatus.toString());
            repository.save(currentRaceStatus);
            callbackService.reportStatus(currentRaceStatus);
        }
    }

    private boolean isActiveAndConsistent(CurrentRaceStatus currentRaceStatus) {
        return currentRaceStatus != null && currentRaceStatus.getRaceActivatedTime() != null && currentRaceStatus.getState() == CurrentRaceStatus.State.ACTIVE;
    }


}
