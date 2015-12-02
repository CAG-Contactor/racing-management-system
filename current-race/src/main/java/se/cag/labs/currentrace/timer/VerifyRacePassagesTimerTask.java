package se.cag.labs.currentrace.timer;


import lombok.extern.log4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import se.cag.labs.currentrace.services.*;
import se.cag.labs.currentrace.services.repository.*;
import se.cag.labs.currentrace.services.repository.datamodel.*;

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
        RaceStatus raceStatus = repository.findByRaceId(RaceStatus.ID);
        if (isActiveAndConsistent(raceStatus)) {
            long currentTime = System.currentTimeMillis();
            if (raceStatus.getStartTime() == null) {
                if (currentTime - raceStatus.getRaceActivatedTime() >= TIME_LIMIT) {
                    log.debug(RaceStatus.Event.TIME_OUT_NOT_STARTED.name());
                    raceStatus.setEvent(RaceStatus.Event.TIME_OUT_NOT_STARTED);
                    raceStatus.setState(RaceStatus.State.INACTIVE);
                }
            }
            if (raceStatus.getMiddleTime() == null && raceStatus.getStartTime() != null) {
                if (currentTime - raceStatus.getStartTime() >= TIME_LIMIT) {
                    log.debug(RaceStatus.Event.DISQUALIFIED.name());
                    raceStatus.setEvent(RaceStatus.Event.DISQUALIFIED);
                    raceStatus.setState(RaceStatus.State.INACTIVE);
                }
            }
            if (raceStatus.getFinishTime() == null && raceStatus.getMiddleTime() != null) {
                if (currentTime - raceStatus.getMiddleTime() >= TIME_LIMIT) {
                    log.debug(RaceStatus.Event.TIME_OUT_NOT_FINISHED.name());
                    raceStatus.setEvent(RaceStatus.Event.TIME_OUT_NOT_FINISHED);
                    raceStatus.setState(RaceStatus.State.INACTIVE);
                }
            }
            log.info(raceStatus.toString());
            repository.save(raceStatus);
            callbackService.reportStatus(raceStatus);
        }
    }

    private boolean isActiveAndConsistent(RaceStatus raceStatus) {
        return raceStatus != null && raceStatus.getRaceActivatedTime() != null && raceStatus.getState() == RaceStatus.State.ACTIVE;
    }


}
