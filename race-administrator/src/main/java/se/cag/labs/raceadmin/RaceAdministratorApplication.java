package se.cag.labs.raceadmin;

import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.data.mongodb.repository.config.*;

@SpringBootApplication
@EnableMongoRepositories()
public class RaceAdministratorApplication {
    public static void main(String[] args) {
        SpringApplication.run(RaceAdministratorApplication.class, args);
    }
}
