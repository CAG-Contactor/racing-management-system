package se.cag.labs.raceadmin.peerservices;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import se.cag.labs.raceadmin.Sensor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SensorService {

  private static final List<Sensor> sensorsList = new ArrayList<>();

  public void registerService(Sensor sensor) {

    // Remove previos registered sensors
    List<Sensor> sensorsToBeRemoved = sensorsList.stream()
      .filter(s -> s.getId().equalsIgnoreCase(sensor.getId()))
      .collect(Collectors.toList());

    sensorsList.removeAll(sensorsToBeRemoved);

    sensor.setIp(sensor.getIp());
    sensorsList.add(sensor);
  }

  public List<Sensor> getSensorsList() {
    return sensorsList;
  }
}
