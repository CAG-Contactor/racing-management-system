package se.cag.labs.currentrace.services.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import se.cag.labs.currentrace.services.repository.datamodel.SensorModel;

import java.util.List;

public interface SensorRepository extends MongoRepository<SensorModel, String> {
  List<SensorModel> findBySensorId(String sensorId);
}
