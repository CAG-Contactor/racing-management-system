package se.cag.springboottry.usermanager;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Project:SpringBootTry
 * User: fredrik
 * Date: 15/11/15
 * Time: 15:51
 */

public interface UserRepository extends MongoRepository<User, String> {
    User findByName(String name);

    @Query("{'email': ?0, 'password': ?1 }")
    User login(String email, String password);
}
