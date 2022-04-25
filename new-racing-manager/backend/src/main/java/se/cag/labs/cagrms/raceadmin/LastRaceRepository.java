package se.cag.labs.cagrms.raceadmin;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LastRaceRepository extends MongoRepository<LastRaceStatus, String> {

    RaceStatus findByUserUserId();
}
