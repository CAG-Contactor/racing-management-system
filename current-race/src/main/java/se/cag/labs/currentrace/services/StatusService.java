package se.cag.labs.currentrace.services;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import se.cag.labs.currentrace.services.repository.*;
import se.cag.labs.currentrace.services.repository.datamodel.*;

@Service
public class StatusService {
    @Autowired
    private CurrentRaceRepository repository;

    public CurrentRaceStatus status() {
        return repository.findByRaceId(CurrentRaceStatus.ID);
    }
}
