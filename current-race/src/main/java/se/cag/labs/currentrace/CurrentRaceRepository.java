package se.cag.labs.currentrace;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface CurrentRaceRepository extends MongoRepository<RaceStatus, String> {
    RaceStatus findByState(RaceStatus.State state);
    RaceStatus findByRaceId(String id);
}
