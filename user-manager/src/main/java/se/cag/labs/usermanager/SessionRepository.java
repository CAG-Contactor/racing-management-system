package se.cag.labs.usermanager;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by dawi on 2015-11-20.
 */
public interface SessionRepository extends MongoRepository<Session, String> {

    Session findByUserId(String userId);

    Session findByToken(String token);
}
