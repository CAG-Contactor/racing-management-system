package se.cag.labs.usermanager;

import org.springframework.data.mongodb.repository.*;
import org.springframework.stereotype.*;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

  User findByUserId(String userId);

  User findByUserIdAndPassword(String userId, String password);
}
