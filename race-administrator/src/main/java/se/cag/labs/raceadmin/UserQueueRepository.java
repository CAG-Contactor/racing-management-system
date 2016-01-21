package se.cag.labs.raceadmin;

import org.springframework.data.mongodb.repository.*;
import org.springframework.stereotype.*;

@Repository
public interface UserQueueRepository extends MongoRepository<User, String> {
  User findUserByUserId(final String userId);
}
