package se.cag.labs.currentrace.timer;


import lombok.extern.log4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import se.cag.labs.currentrace.apicontroller.apimodel.RaceStatus;
import se.cag.labs.currentrace.services.CallbackService;
import se.cag.labs.currentrace.services.repository.CurrentRaceRepository;
import se.cag.labs.currentrace.services.repository.datamodel.CurrentRaceStatus;

import java.util.*;

@Component
@Log4j
public class VerifyRacePassagesTimerTask extends TimerTask {

    public static final long TIME_INTERVAL = 10 * 1000;
    public static final long TIME_LIMIT = 3 * 60 * 1000;

    @Autowired
    private CurrentRaceRepository repository;

    @Autowired
    private CallbackService callbackService;

    @Override
    public void run() {
        CurrentRaceStatus previousState = repository.findByRaceId(CurrentRaceStatus.ID);
        if (previousState != null) {
            CurrentRaceStatus newState = previousState.toBuilder().build();
            checkStateChange(previousState, newState);
        }
    }

    public void trigUpdate(final CurrentRaceStatus previousState) {
        CurrentRaceStatus newState = repository.findByRaceId(CurrentRaceStatus.ID);
        checkStateChange(previousState, newState);
    }

    public void checkStateChange(final CurrentRaceStatus previousState, final CurrentRaceStatus newState) {
        if (isActiveAndConsistent(newState)) {
            long currentTime = System.currentTimeMillis();
            if (newState.getStartTime() == null) {
                if (currentTime - newState.getRaceActivatedTime() >= TIME_LIMIT) {
                    log.debug(RaceStatus.Event.TIME_OUT_NOT_STARTED.name());
                    newState.setEvent(RaceStatus.Event.TIME_OUT_NOT_STARTED);
                    newState.setState(RaceStatus.State.INACTIVE);
                }
            }
            if (newState.getSplitTime() == null && newState.getStartTime() != null) {
                if (currentTime - newState.getStartTime() >= TIME_LIMIT) {
                    log.debug(RaceStatus.Event.DISQUALIFIED.name());
                    newState.setEvent(RaceStatus.Event.DISQUALIFIED);
                    newState.setState(RaceStatus.State.INACTIVE);
                }
            }
            if (newState.getFinishTime() == null && newState.getSplitTime() != null) {
                if (currentTime - newState.getSplitTime() >= TIME_LIMIT) {
                    log.debug(RaceStatus.Event.TIME_OUT_NOT_FINISHED.name());
                    newState.setEvent(RaceStatus.Event.TIME_OUT_NOT_FINISHED);
                    newState.setState(RaceStatus.State.INACTIVE);
                }
            }
            if (!newState.equals(previousState)) {
                log.info(newState.toString());
                repository.save(newState);
                callbackService.reportStatus(newState);
            }
        }
    }

    private boolean isActiveAndConsistent(CurrentRaceStatus currentRaceStatus) {
        return currentRaceStatus != null && currentRaceStatus.getRaceActivatedTime() != null && currentRaceStatus.getState() == RaceStatus.State.ACTIVE;
    }


}
