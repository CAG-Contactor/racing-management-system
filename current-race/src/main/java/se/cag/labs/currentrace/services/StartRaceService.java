package se.cag.labs.currentrace.services;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.cag.labs.currentrace.services.repository.CurrentRaceRepository;
import se.cag.labs.currentrace.services.repository.datamodel.RaceStatus;

@Service
@Log
public class StartRaceService {
    @Autowired
    private CurrentRaceRepository repository;
    @Autowired
    private TimerService timerService;

    public enum ReturnStatus {
        STARTED,
        FOUND
    }

    public ReturnStatus startRace(String callbackUrl) {
        RaceStatus activeRaceStatus = repository.findByRaceId(RaceStatus.ID);
        timerService.startTimer();
        if (activeRaceStatus == null) {
            RaceStatus status = new RaceStatus();
            status.setState(RaceStatus.State.ACTIVE);
            status.setRaceActivatedTime(System.currentTimeMillis());
            status.setCallbackUrl(callbackUrl);
            repository.save(status);
            log.info("Starting race: " + callbackUrl);
            return ReturnStatus.STARTED;
        } else if (RaceStatus.State.INACTIVE.equals(activeRaceStatus.getState())) {
            activeRaceStatus.setCallbackUrl(callbackUrl);
            activeRaceStatus.setEvent(RaceStatus.Event.NONE);
            activeRaceStatus.setState(RaceStatus.State.ACTIVE);
            activeRaceStatus.setRaceActivatedTime(System.currentTimeMillis());
            activeRaceStatus.setStartTime(null);
            activeRaceStatus.setMiddleTime(null);
            activeRaceStatus.setFinishTime(null);

            repository.save(activeRaceStatus);
            log.info("Restarting race");
            return ReturnStatus.STARTED;
        } else {
            log.info("Race is already started");
            return ReturnStatus.FOUND;
        }
    }
}
