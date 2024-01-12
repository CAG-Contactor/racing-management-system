package se.cag.labs.currentrace.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import se.cag.labs.currentrace.apicontroller.apimodel.Sensor;
import se.cag.labs.currentrace.apicontroller.apimodel.SensorResponse;
import se.cag.labs.currentrace.apicontroller.mapper.SensorMapper;
import se.cag.labs.currentrace.services.repository.SensorRepository;
import se.cag.labs.currentrace.services.repository.datamodel.SensorModel;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class SensorService {

  @Autowired
  private SensorRepository sensorRepository;

  private static final int ONE_HOUR = 1000*60*60;

  public ReturnStatus registerSensor(Sensor sensor) {
    SensorModel sensorModel = SensorMapper.createModelFromApi(sensor);

    if (sensorModel != null) {
      List<SensorModel> sensorModels = sensorRepository.findBySensorId(sensor.getSensorId());
      sensorModels.stream()
        .map(SensorModel::getSensorId)
        .forEach(sensorId -> sensorRepository.deleteById(sensorId));
      sensorRepository.save(sensorModel);
      return ReturnStatus.REGISTERED;
    } else {
      return ReturnStatus.ILLEGAL;
    }
  }

  @Scheduled(fixedDelay = ONE_HOUR)
  public void deleteOldSensors() {
    List<SensorModel> sensorModels = sensorRepository.findByRegisteredTimestampLessThan(System.currentTimeMillis() - ONE_HOUR);
    sensorModels.stream()
      .map(SensorModel::getSensorId)
      .forEach(sensorId -> sensorRepository.deleteById(sensorId));
  }

  public List<SensorResponse> getSensorList() {
    return sensorRepository.findAll().stream()
      .map(SensorMapper::createSensorResponseFromModel)
      .collect(toList());
  }

  public enum ReturnStatus {
    REGISTERED,
    ILLEGAL
  }
}
