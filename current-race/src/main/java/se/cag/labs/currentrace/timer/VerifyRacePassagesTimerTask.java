package se.cag.labs.currentrace.timer;


import lombok.extern.log4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import se.cag.labs.currentrace.services.*;
import se.cag.labs.currentrace.services.repository.*;
import se.cag.labs.currentrace.services.repository.datamodel.*;
import lombok.extern.java.Log;
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
        CurrentRaceStatus currentRaceStatus = repository.findByRaceId(CurrentRaceStatus.ID);
        if (isActiveAndConsistent(currentRaceStatus)) {
            long currentTime = System.currentTimeMillis();
            if (currentRaceStatus.getStartTime() == null) {
                if (currentTime - currentRaceStatus.getRaceActivatedTime() >= TIME_LIMIT) {
                    log.debug(RaceStatus.Event.TIME_OUT_NOT_STARTED.name());
                    currentRaceStatus.setEvent(RaceStatus.Event.TIME_OUT_NOT_STARTED);
                    currentRaceStatus.setState(RaceStatus.State.INACTIVE);
                }
            }
            if (currentRaceStatus.getMiddleTime() == null && currentRaceStatus.getStartTime() != null) {
                if (currentTime - currentRaceStatus.getStartTime() >= TIME_LIMIT) {
                    log.debug(RaceStatus.Event.DISQUALIFIED.name());
                    currentRaceStatus.setEvent(RaceStatus.Event.DISQUALIFIED);
                    currentRaceStatus.setState(RaceStatus.State.INACTIVE);
                }
            }
            if (currentRaceStatus.getFinishTime() == null && currentRaceStatus.getMiddleTime() != null) {
                if (currentTime - currentRaceStatus.getMiddleTime() >= TIME_LIMIT) {
                    log.debug(RaceStatus.Event.TIME_OUT_NOT_FINISHED.name());
                    currentRaceStatus.setEvent(RaceStatus.Event.TIME_OUT_NOT_FINISHED);
                    currentRaceStatus.setState(RaceStatus.State.INACTIVE);
                }
            }
            log.info(currentRaceStatus.toString());
            repository.save(currentRaceStatus);
            callbackService.reportStatus(currentRaceStatus);
        }
    }

    private boolean isActiveAndConsistent(CurrentRaceStatus currentRaceStatus) {
        return currentRaceStatus != null && currentRaceStatus.getRaceActivatedTime() != null && currentRaceStatus.getState() == RaceStatus.State.ACTIVE;
    }


}
