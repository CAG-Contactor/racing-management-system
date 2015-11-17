package se.cag.springboottry;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories()
public class RMSApplication {

    public static void main(String[] args) {
        SpringApplication.run(RMSApplication.class, args);
    }
}
