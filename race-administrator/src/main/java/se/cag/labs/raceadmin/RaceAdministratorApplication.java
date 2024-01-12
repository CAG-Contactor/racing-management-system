package se.cag.labs.raceadmin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories()
public class RaceAdministratorApplication {
  public static void main(String[] args) {
    SpringApplication.run(RaceAdministratorApplication.class, args);
  }
}
