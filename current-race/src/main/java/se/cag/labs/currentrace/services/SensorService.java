package se.cag.labs.currentrace.services;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import se.cag.labs.currentrace.apicontroller.apimodel.Sensor;
import se.cag.labs.currentrace.apicontroller.apimodel.SensorResponse;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SensorService {

  private static final List<SensorResponse> sensorsList = new ArrayList<>();

  public void registerSensor(Sensor sensor) {

    // Remove previos registered sensors with given id
    List<SensorResponse> sensorsToBeRemoved = sensorsList.stream()
      .filter(s -> s.getId().equalsIgnoreCase(sensor.getId()))
      .collect(Collectors.toList());

    sensorsList.removeAll(sensorsToBeRemoved);

    // Crop sensor id and ip
    sensorsList.add(SensorResponse.builder()
      .id(StringUtils.substring(sensor.getId(), 0, 16))
      .ip(StringUtils.substring(sensor.getIp(), 0, 16))
      .registeredDate(new Date())
      .build());
  }

  public List<SensorResponse> getSensorList() {
    return sensorsList;
  }
}
