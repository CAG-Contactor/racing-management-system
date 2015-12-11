package se.cag.labs.usermanager;

import org.springframework.data.mongodb.repository.*;
import org.springframework.stereotype.*;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

    User findByName(String username);

    User findByNameAndPassword(String username, String password);
}
