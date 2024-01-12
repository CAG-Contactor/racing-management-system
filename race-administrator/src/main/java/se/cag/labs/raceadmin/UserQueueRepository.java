package se.cag.labs.raceadmin;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserQueueRepository extends MongoRepository<User, String> {
  User findUserByUserId(final String userId);
}
