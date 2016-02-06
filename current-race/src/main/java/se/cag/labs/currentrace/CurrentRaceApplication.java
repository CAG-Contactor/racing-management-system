package se.cag.labs.currentrace;

import lombok.extern.log4j.Log4j;
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.mongodb.repository.config.*;
import se.cag.labs.currentrace.services.CancelRaceService;

@SpringBootApplication
@EnableMongoRepositories()
@Log4j
public class CurrentRaceApplication {
  public static void main(String[] args) {
    ConfigurableApplicationContext app = SpringApplication.run(CurrentRaceApplication.class, args);
    CancelRaceService cancelRaceService = (CancelRaceService) app.getBean("cancelRaceService");
    cancelRaceService.cancelRace();
  }
}
