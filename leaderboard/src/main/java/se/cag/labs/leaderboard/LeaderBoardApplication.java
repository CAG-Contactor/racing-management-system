package se.cag.labs.leaderboard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories()
public class LeaderBoardApplication {
  public static void main(String[] args) {
    SpringApplication.run(LeaderBoardApplication.class, args);
  }
}
