package se.cag.labs.currentrace.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import se.cag.labs.currentrace.CurrentRaceRepository;
import se.cag.labs.currentrace.RaceStatus;

@Service
public class CancelRaceService {
    @Autowired
    private CurrentRaceRepository repository;

    public ResponseEntity cancelRace() {
        RaceStatus raceStatus = repository.findByRaceId(RaceStatus.ID);

        if(raceStatus != null) {
            raceStatus.setFinishTime(null);
            raceStatus.setMiddleTime(null);
            raceStatus.setStartTime(null);
            raceStatus.setEvent(null);
            raceStatus.setState(RaceStatus.State.INACTIVE);

            repository.save(raceStatus);
            return new ResponseEntity(HttpStatus.ACCEPTED);
        }

        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }
}
