package se.cag.labs.raceadmin;

import org.springframework.data.mongodb.repository.*;
import org.springframework.stereotype.*;

@Repository
public interface ActiveRaceRepository extends MongoRepository<RaceStatus, String> {
    
}
