package se.cag.labs.currentrace.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import se.cag.labs.currentrace.CurrentRaceRepository;
import se.cag.labs.currentrace.RaceStatus;

import java.util.Date;

@Service
public class PassageDetectedService {
    @Autowired
    private CurrentRaceRepository repository;

    public ResponseEntity passageDetected(String sensorID, long timestamp) {
        RaceStatus raceStatus = repository.findByRaceId(RaceStatus.ID);

        if(RaceStatus.State.ACTIVE.equals(raceStatus.getState())) {
            switch (sensorID) {
                case "START_ID":
                    if(!RaceStatus.Event.START.equals(raceStatus.getEvent())) {
                        raceStatus.setStartTime(new Date(timestamp));
                        raceStatus.setEvent(RaceStatus.Event.START);
                    }
                    break;
                case "MIDDLE_ID":
                    if(!RaceStatus.Event.MIDDLE.equals(raceStatus.getEvent())) {
                        raceStatus.setMiddleTime(new Date(timestamp));
                        raceStatus.setEvent(RaceStatus.Event.MIDDLE);
                    }
                    break;
                case "FINISH_ID":
                    if(!RaceStatus.Event.FINISH.equals(raceStatus.getEvent())) {
                        raceStatus.setFinishTime(new Date(timestamp));
                        raceStatus.setEvent(RaceStatus.Event.FINISH);
                        raceStatus.setState(RaceStatus.State.INACTIVE);
                    }
                    break;
                default:
                    return new ResponseEntity(HttpStatus.NOT_ACCEPTABLE);
            }
        }

        repository.save(raceStatus);

        return new ResponseEntity(HttpStatus.ACCEPTED);
    }

    public enum PassageDetectedStatus {

    }
}
