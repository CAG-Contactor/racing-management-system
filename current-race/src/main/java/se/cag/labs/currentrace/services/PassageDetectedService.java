package se.cag.labs.currentrace.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.cag.labs.currentrace.services.repository.CurrentRaceRepository;
import se.cag.labs.currentrace.services.repository.datamodel.RaceStatus;


@Service
public class PassageDetectedService {
    private static final String START_ID = "START_ID";
    private static final String MIDDLE_ID = "MIDDLE_ID";
    private static final String FINISH_ID = "FINISH_ID";

    @Autowired
    private CurrentRaceRepository repository;

    public enum PassageDetectedStatus {
        ACCEPTED,
        IGNORED,
        ERROR
    }

    public PassageDetectedStatus passageDetected(String sensorID, long timestamp) {
        RaceStatus raceStatus = repository.findByRaceId(RaceStatus.ID);

        if (RaceStatus.State.ACTIVE.equals(raceStatus.getState())) {
            boolean isModified = false;
            switch (sensorID) {
                case START_ID:
                    if (!RaceStatus.Event.START.equals(raceStatus.getEvent())) {
                        raceStatus.setStartTime(timestamp);
                        raceStatus.setEvent(RaceStatus.Event.START);
                        isModified = true;
                    }
                    break;
                case MIDDLE_ID:
                    if (!RaceStatus.Event.MIDDLE.equals(raceStatus.getEvent())) {
                        raceStatus.setMiddleTime(timestamp);
                        raceStatus.setEvent(RaceStatus.Event.MIDDLE);
                        isModified = true;
                    }
                    break;
                case FINISH_ID:
                    if (!RaceStatus.Event.FINISH.equals(raceStatus.getEvent())) {
                        raceStatus.setFinishTime(timestamp);
                        RaceStatus.Event event = raceStatus.getMiddleTime() == null ? RaceStatus.Event.DISQUALIFIED : RaceStatus.Event.FINISH;
                        raceStatus.setEvent(event);
                        raceStatus.setState(RaceStatus.State.INACTIVE);
                        isModified = true;
                    }
                    break;
                default:
                    return PassageDetectedStatus.ERROR;
            }

            if (isModified) {
                repository.save(raceStatus);
                return PassageDetectedStatus.ACCEPTED;
            }
        }

        return PassageDetectedStatus.IGNORED;
    }
}
