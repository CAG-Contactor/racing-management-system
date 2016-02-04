package se.cag.labs.currentrace.services;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import se.cag.labs.currentrace.apicontroller.apimodel.Sensor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SensorService {

  private static final List<Sensor> sensorsList = new ArrayList<>();

  public void registerSensor(Sensor sensor) {

    // Remove previos registered sensors with given id
    List<Sensor> sensorsToBeRemoved = sensorsList.stream()
      .filter(s -> s.getId().equalsIgnoreCase(sensor.getId()))
      .collect(Collectors.toList());

    sensorsList.removeAll(sensorsToBeRemoved);

    // Crop sensor id and ip
    sensor.setIp(StringUtils.substring(sensor.getIp(), 0, 16));
    sensor.setId(StringUtils.substring(sensor.getId(), 0, 16));
    sensorsList.add(sensor);
  }

  public List<Sensor> getSensorList() {
    return sensorsList;
  }
}
