package se.cag.labs.currentrace.services;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.cag.labs.currentrace.services.repository.CurrentRaceRepository;
import se.cag.labs.currentrace.services.repository.datamodel.RaceStatus;
import se.cag.labs.currentrace.services.statuses.StartRaceReturnStatus;

@Service
@Log
public class StartRaceService {
    @Autowired
    private CurrentRaceRepository repository;

    public StartRaceReturnStatus startRace(String callbackUrl) {
        RaceStatus activeRaceStatus = repository.findByRaceId(RaceStatus.ID);

        if (activeRaceStatus == null) {
            RaceStatus status = new RaceStatus();
            status.setState(RaceStatus.State.ACTIVE);
            repository.save(status);
            log.info("Starting race: " + callbackUrl);
            return StartRaceReturnStatus.STARTED;
        } else if (RaceStatus.State.INACTIVE.equals(activeRaceStatus.getState())) {
            activeRaceStatus.setEvent(RaceStatus.Event.NONE);
            activeRaceStatus.setState(RaceStatus.State.ACTIVE);
            activeRaceStatus.setStartTime(null);
            activeRaceStatus.setMiddleTime(null);
            activeRaceStatus.setFinishTime(null);

            repository.save(activeRaceStatus);
            log.info("Restarting race");
            return StartRaceReturnStatus.STARTED;
        } else {
            log.info("Race is already started");
            return StartRaceReturnStatus.FOUND;
        }
    }
}
