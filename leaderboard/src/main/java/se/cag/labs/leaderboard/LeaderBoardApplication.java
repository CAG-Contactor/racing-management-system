package se.cag.labs.leaderboard;

import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.data.mongodb.repository.config.*;

@SpringBootApplication
@EnableMongoRepositories()
public class LeaderBoardApplication {
  public static void main(String[] args) {
    SpringApplication.run(LeaderBoardApplication.class, args);
  }
}
