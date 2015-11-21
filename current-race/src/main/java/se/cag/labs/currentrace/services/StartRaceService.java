package se.cag.labs.currentrace.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.cag.labs.currentrace.CurrentRaceRepository;
import se.cag.labs.currentrace.datamodel.RaceStatus;

@Service
public class StartRaceService {

    @Autowired
    private CurrentRaceRepository repository;

    public enum StartRaceReturnStatus {
        STARTED,
        FOUND
    }

    public StartRaceReturnStatus startRace(String callbackUrl) {
        RaceStatus activeRaceStatus = repository.findByRaceId(RaceStatus.ID);

        if (activeRaceStatus == null) {
            RaceStatus status = new RaceStatus();
            status.setState(RaceStatus.State.ACTIVE);
            repository.save(status);
            System.out.println("Starting race: " + callbackUrl);
            return StartRaceReturnStatus.STARTED;
        } else if (RaceStatus.State.INACTIVE.equals(activeRaceStatus.getState())) {
            activeRaceStatus.setEvent(RaceStatus.Event.NONE);
            activeRaceStatus.setState(RaceStatus.State.ACTIVE);
            activeRaceStatus.setStartTime(null);
            activeRaceStatus.setMiddleTime(null);
            activeRaceStatus.setFinishTime(null);

            repository.save(activeRaceStatus);
            System.out.println("Restarting race");
            return StartRaceReturnStatus.STARTED;
        } else {
            System.out.println("Race is already started");
            return StartRaceReturnStatus.FOUND;
        }
    }
}
