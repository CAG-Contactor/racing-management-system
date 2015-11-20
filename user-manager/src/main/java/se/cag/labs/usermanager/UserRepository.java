package se.cag.labs.usermanager;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface UserRepository extends MongoRepository<User, String> {

    User findByName(String username);

    User findByNameAndPassword(String username, String password);
}
