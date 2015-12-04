package se.cag.labs.currentrace.services;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.cag.labs.currentrace.services.repository.CurrentRaceRepository;
import se.cag.labs.currentrace.services.repository.datamodel.CurrentRaceStatus;

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
        CurrentRaceStatus activeCurrentRaceStatus = repository.findByRaceId(CurrentRaceStatus.ID);
        timerService.startTimer();
        if (activeCurrentRaceStatus == null) {
            CurrentRaceStatus status = new CurrentRaceStatus();
            status.setState(CurrentRaceStatus.State.ACTIVE);
            status.setRaceActivatedTime(System.currentTimeMillis());
            status.setCallbackUrl(callbackUrl);
            repository.save(status);
            log.info("Starting race: " + callbackUrl);
            return ReturnStatus.STARTED;
        } else if (CurrentRaceStatus.State.INACTIVE.equals(activeCurrentRaceStatus.getState())) {
            activeCurrentRaceStatus.setCallbackUrl(callbackUrl);
            activeCurrentRaceStatus.setEvent(CurrentRaceStatus.Event.NONE);
            activeCurrentRaceStatus.setState(CurrentRaceStatus.State.ACTIVE);
            activeCurrentRaceStatus.setRaceActivatedTime(System.currentTimeMillis());
            activeCurrentRaceStatus.setStartTime(null);
            activeCurrentRaceStatus.setMiddleTime(null);
            activeCurrentRaceStatus.setFinishTime(null);

            repository.save(activeCurrentRaceStatus);
            log.info("Restarting race");
            return ReturnStatus.STARTED;
        } else {
            log.info("Race is already started");
            return ReturnStatus.FOUND;
        }
    }
}
