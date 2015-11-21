package se.cag.labs.currentrace.services.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import se.cag.labs.currentrace.services.repository.datamodel.RaceStatus;

public interface CurrentRaceRepository extends MongoRepository<RaceStatus, String> {
    RaceStatus findByState(RaceStatus.State state);

    RaceStatus findByRaceId(String id);
}
