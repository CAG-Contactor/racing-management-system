package se.cag.labs.currentrace.services;

import org.springframework.beans.factory.annotation.Autowired;
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

  public ReturnStatus registerSensor(Sensor sensor) {
    SensorModel sensorModel = SensorMapper.createModelFromApi(sensor);

    if (sensorModel != null) {
      List<SensorModel> sensorModels = sensorRepository.findBySensorId(sensor.getSensorId());
      sensorRepository.delete(sensorModels);
      sensorRepository.save(sensorModel);
      return ReturnStatus.REGISTERED;
    }
    else {
      return ReturnStatus.ILLEGAL;
    }
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
