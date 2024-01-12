package se.cag.labs.raceadmin;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActiveRaceRepository extends MongoRepository<RaceStatus, String> {

  RaceStatus findByUserUserId();
}
