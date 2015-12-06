package se.cag.labs.currentrace.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.cag.labs.currentrace.services.repository.CurrentRaceRepository;
import se.cag.labs.currentrace.services.repository.datamodel.CurrentRaceStatus;

@Service
public class StatusService {
    @Autowired
    private CurrentRaceRepository repository;

    public CurrentRaceStatus status() {
        return repository.findByRaceId(CurrentRaceStatus.ID);
    }
}
