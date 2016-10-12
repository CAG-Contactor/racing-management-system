package se.cag.labs.currentrace.services.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import se.cag.labs.currentrace.apicontroller.apimodel.RaceStatus;
import se.cag.labs.currentrace.services.repository.datamodel.CurrentRaceStatus;

@Repository
public interface CurrentRaceRepository extends MongoRepository<CurrentRaceStatus, String> {
  CurrentRaceStatus findByState(RaceStatus.State state);

  CurrentRaceStatus findByRaceId(String id);
}
