package se.cag.labs.currentrace;

import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.data.mongodb.repository.config.*;

@SpringBootApplication
@EnableMongoRepositories()
public class CurrentRaceApplication {
    public static void main(String[] args) {
        SpringApplication.run(CurrentRaceApplication.class, args);
    }
}
