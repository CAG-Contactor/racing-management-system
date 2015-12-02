package se.cag.labs.currentrace.services.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.*;
import se.cag.labs.currentrace.services.repository.datamodel.RaceStatus;

@Repository
public interface CurrentRaceRepository extends MongoRepository<RaceStatus, String> {
    RaceStatus findByState(RaceStatus.State state);

    RaceStatus findByRaceId(String id);
}
