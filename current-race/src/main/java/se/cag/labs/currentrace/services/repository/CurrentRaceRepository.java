package se.cag.labs.currentrace.services.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.*;
import se.cag.labs.currentrace.services.repository.datamodel.CurrentRaceStatus;

@Repository
public interface CurrentRaceRepository extends MongoRepository<CurrentRaceStatus, String> {
    CurrentRaceStatus findByState(CurrentRaceStatus.State state);

    CurrentRaceStatus findByRaceId(String id);
}
