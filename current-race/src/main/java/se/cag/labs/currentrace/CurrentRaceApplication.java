package se.cag.labs.currentrace;

import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import se.cag.labs.currentrace.services.CancelRaceService;

@SpringBootApplication
@EnableMongoRepositories
@EnableScheduling
@Slf4j
public class CurrentRaceApplication {
  public static void main(String[] args) {
    ConfigurableApplicationContext app = SpringApplication.run(CurrentRaceApplication.class, args);
    CancelRaceService cancelRaceService = (CancelRaceService) app.getBean("cancelRaceService");
    cancelRaceService.cancelRace();
  }
}
