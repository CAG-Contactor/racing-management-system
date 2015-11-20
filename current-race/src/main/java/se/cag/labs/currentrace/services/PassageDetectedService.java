package se.cag.labs.currentrace.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.cag.labs.currentrace.CurrentRaceRepository;
import se.cag.labs.currentrace.RaceStatus;

import java.util.Date;

@Service
public class PassageDetectedService {
    @Autowired
    private CurrentRaceRepository repository;

    public PassageDetectedStatus passageDetected(String sensorID, long timestamp) {
        RaceStatus raceStatus = repository.findByRaceId(RaceStatus.ID);

        if (RaceStatus.State.ACTIVE.equals(raceStatus.getState())) {
            switch (sensorID) {
                case "START_ID":
                    if (!RaceStatus.Event.START.equals(raceStatus.getEvent())) {
                        raceStatus.setStartTime(new Date(timestamp));
                        raceStatus.setEvent(RaceStatus.Event.START);
                    }
                    break;
                case "MIDDLE_ID":
                    if (!RaceStatus.Event.MIDDLE.equals(raceStatus.getEvent())) {
                        raceStatus.setMiddleTime(new Date(timestamp));
                        raceStatus.setEvent(RaceStatus.Event.MIDDLE);
                    }
                    break;
                case "FINISH_ID":
                    if (!RaceStatus.Event.FINISH.equals(raceStatus.getEvent())) {
                        raceStatus.setFinishTime(new Date(timestamp));
                        RaceStatus.Event event = raceStatus.getMiddleTime() == null ? RaceStatus.Event.DISQUALIFIED : RaceStatus.Event.FINISH;
                        raceStatus.setEvent(event);
                        raceStatus.setState(RaceStatus.State.INACTIVE);
                    }
                    break;
                default:
                    return PassageDetectedStatus.ERROR;
            }
        }

        repository.save(raceStatus);

        return PassageDetectedStatus.ACCEPTED;
    }

    public enum PassageDetectedStatus {
        ACCEPTED,
        ERROR
    }
}
