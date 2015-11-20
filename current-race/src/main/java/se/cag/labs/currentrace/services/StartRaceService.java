package se.cag.labs.currentrace.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import se.cag.labs.currentrace.CurrentRaceRepository;
import se.cag.labs.currentrace.RaceStatus;

import java.util.Date;

@Service
public class StartRaceService {
    @Autowired
    private CurrentRaceRepository repository;

    public ResponseEntity startRace(String callbackUrl) {
        RaceStatus activeRaceStatus = repository.findByRaceId(RaceStatus.ID);

        if(activeRaceStatus == null) {
            repository.save(new RaceStatus(RaceStatus.Event.START, new Date(), null, null, RaceStatus.State.ACTIVE));
            System.out.println("Starting race: " + callbackUrl);
            return new ResponseEntity(HttpStatus.ACCEPTED);
        } else if(RaceStatus.State.INACTIVE.equals(activeRaceStatus.getState())) {
            activeRaceStatus.setEvent(RaceStatus.Event.START);
            activeRaceStatus.setState(RaceStatus.State.ACTIVE);
            activeRaceStatus.setStartTime(new Date());
            activeRaceStatus.setMiddleTime(null);
            activeRaceStatus.setFinishTime(null);

            repository.save(activeRaceStatus);
            System.out.println("Restarting race");
            return new ResponseEntity(HttpStatus.ACCEPTED);
        } else {
            System.out.println("Race is already started");
            return new ResponseEntity(HttpStatus.FOUND);
        }
    }
}
