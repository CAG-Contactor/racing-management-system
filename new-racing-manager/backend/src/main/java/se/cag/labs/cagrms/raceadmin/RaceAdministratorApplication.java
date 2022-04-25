package se.cag.labs.cagrms.raceadmin;

@SpringBootApplication
@EnableMongoRepositories()
public class RaceAdministratorApplication {
  public static void main(String[] args) {
    SpringApplication.run(RaceAdministratorApplication.class, args);
  }
}
