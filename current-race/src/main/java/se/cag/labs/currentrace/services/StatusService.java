package se.cag.labs.currentrace.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.cag.labs.currentrace.CurrentRaceRepository;
import se.cag.labs.currentrace.datamodel.RaceStatus;

@Service
public class StatusService {
    @Autowired
    private CurrentRaceRepository repository;

    public RaceStatus status() {
        return repository.findByRaceId(RaceStatus.ID);
    }
}
