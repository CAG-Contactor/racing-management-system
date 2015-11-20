package se.cag.labs.leaderboard;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface LeaderBoardRepository extends MongoRepository<UserResult, String> {
}
