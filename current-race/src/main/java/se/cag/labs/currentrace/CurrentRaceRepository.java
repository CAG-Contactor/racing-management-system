package se.cag.labs.currentrace;

import org.springframework.data.mongodb.repository.MongoRepository;
import se.cag.labs.currentrace.datamodel.RaceStatus;

public interface CurrentRaceRepository extends MongoRepository<RaceStatus, String> {
    RaceStatus findByState(RaceStatus.State state);

    RaceStatus findByRaceId(String id);
}
