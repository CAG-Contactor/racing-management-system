package se.cag.labs.currentrace;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories()
public class CurrentRaceApplication {
    public static void main(String[] args) {
        SpringApplication.run(CurrentRaceApplication.class, args);
    }
}
