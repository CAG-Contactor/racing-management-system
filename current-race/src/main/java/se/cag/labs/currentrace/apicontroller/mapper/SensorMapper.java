package se.cag.labs.currentrace.apicontroller.mapper;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.InetAddressValidator;
import se.cag.labs.currentrace.apicontroller.apimodel.Sensor;
import se.cag.labs.currentrace.apicontroller.apimodel.SensorResponse;
import se.cag.labs.currentrace.services.repository.datamodel.SensorModel;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

public final class SensorMapper {
  private SensorMapper() {
    //Not used
  }

  public static SensorModel createModelFromApi(Sensor sensor) {
    if (StringUtils.isNotBlank(sensor.getSensorId()) && StringUtils.length(sensor.getSensorId()) < 128) {
      if (InetAddressValidator.getInstance().isValidInet4Address(sensor.getSensorIpAddress())) {
        return SensorModel.builder()
          .sensorId(sensor.getSensorId())
          .sensorIpAddress(sensor.getSensorIpAddress())
          .registeredTimestamp(System.currentTimeMillis())
          .build();

      }
    }
    return null;
  }

  public static SensorResponse createSensorResponseFromModel(SensorModel sensorModel) {
    return SensorResponse.builder()
      .sensorId(sensorModel.getSensorId())
      .sensorIpAddress(sensorModel.getSensorIpAddress())
      .registeredDate(LocalDateTime.ofInstant(Instant.ofEpochMilli(sensorModel.getRegisteredTimestamp()), ZoneId.systemDefault()))
      .build();
  }

}
