package se.cag.labs.currentrace.services;

import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.cag.labs.currentrace.apicontroller.apimodel.RaceStatus;
import se.cag.labs.currentrace.services.repository.CurrentRaceRepository;
import se.cag.labs.currentrace.services.repository.datamodel.CurrentRaceStatus;

@Service
@Log4j
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
        if (activeCurrentRaceStatus == null) {
            CurrentRaceStatus status = new CurrentRaceStatus();
            status.setState(RaceStatus.State.ACTIVE);
            status.setRaceActivatedTime(System.currentTimeMillis());
            status.setCallbackUrl(callbackUrl);
            repository.save(status);
            log.info("Starting race: " + callbackUrl);
            timerService.trigAsyncStatusUpdate();
            return ReturnStatus.STARTED;
        } else if (RaceStatus.State.INACTIVE.equals(activeCurrentRaceStatus.getState())) {
            activeCurrentRaceStatus.setCallbackUrl(callbackUrl);
            activeCurrentRaceStatus.setEvent(RaceStatus.Event.NONE);
            activeCurrentRaceStatus.setState(RaceStatus.State.ACTIVE);
            activeCurrentRaceStatus.setRaceActivatedTime(System.currentTimeMillis());
            activeCurrentRaceStatus.setStartTime(null);
            activeCurrentRaceStatus.setSplitTime(null);
            activeCurrentRaceStatus.setFinishTime(null);

            repository.save(activeCurrentRaceStatus);
            log.info("Restarting race");
            timerService.trigAsyncStatusUpdate();
            return ReturnStatus.STARTED;
        } else {
            log.info("Race is already started");
            timerService.trigAsyncStatusUpdate();
            return ReturnStatus.FOUND;
        }
    }
}
