package se.cag.labs.raceadmin;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.*;
import se.cag.labs.usermanager.*;

@Repository
public interface RaceRepository extends MongoRepository<User, String> {
    
}
