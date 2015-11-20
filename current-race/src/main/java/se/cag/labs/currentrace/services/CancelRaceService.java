package se.cag.labs.currentrace.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.cag.labs.currentrace.CurrentRaceRepository;
import se.cag.labs.currentrace.RaceStatus;

@Service
public class CancelRaceService {
    @Autowired
    private CurrentRaceRepository repository;

    public void cancelRace() {
        RaceStatus raceStatus = repository.findByRaceId(RaceStatus.ID);

        if(raceStatus != null) {
            raceStatus.setFinishTime(null);
            raceStatus.setMiddleTime(null);
            raceStatus.setStartTime(null);
            raceStatus.setEvent(null);
            raceStatus.setState(RaceStatus.State.INACTIVE);

            repository.save(raceStatus);
        }
    }
}
