package se.cag.labs.leaderboard;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.*;

@Repository
public interface LeaderBoardRepository extends MongoRepository<UserResult, String> {
}
