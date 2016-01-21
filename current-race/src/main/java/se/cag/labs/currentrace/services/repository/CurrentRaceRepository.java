package se.cag.labs.currentrace.services.repository;

import org.springframework.data.mongodb.repository.*;
import org.springframework.stereotype.*;
import se.cag.labs.currentrace.apicontroller.apimodel.*;
import se.cag.labs.currentrace.services.repository.datamodel.*;

@Repository
public interface CurrentRaceRepository extends MongoRepository<CurrentRaceStatus, String> {
    CurrentRaceStatus findByState(RaceStatus.State state);

    CurrentRaceStatus findByRaceId(String id);
}
