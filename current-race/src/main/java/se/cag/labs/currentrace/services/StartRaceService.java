package se.cag.labs.currentrace.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.cag.labs.currentrace.CurrentRaceRepository;
import se.cag.labs.currentrace.RaceStatus;

import java.util.Date;

@Service
public class StartRaceService {
    @Autowired
    private CurrentRaceRepository repository;

    public String startRace(String callbackUrl) {
        RaceStatus activeRaceStatus = repository.findByState(RaceStatus.State.ACTIVE);

        if(activeRaceStatus == null) {
            repository.save(new RaceStatus(RaceStatus.Event.START, new Date(), null, null, RaceStatus.State.ACTIVE));
            return "Starting race: " + callbackUrl;
        } else {
            return "Race is already started";
        }
    }
}
