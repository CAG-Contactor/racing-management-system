package se.cag.labs.raceadmin;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface RaceRepository extends MongoRepository<RaceStatus, String> {
}
