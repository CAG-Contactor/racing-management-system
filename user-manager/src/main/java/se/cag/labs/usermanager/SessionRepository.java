package se.cag.labs.usermanager;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.*;

@Repository
public interface SessionRepository extends MongoRepository<Session, String> {

    Session findByUserId(String userId);

    Session findByToken(String token);
}
