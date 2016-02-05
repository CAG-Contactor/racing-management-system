package se.cag.labs.currentrace.apicontroller.mapper;

import org.apache.commons.lang3.StringUtils;
import se.cag.labs.currentrace.apicontroller.apimodel.Sensor;
import se.cag.labs.currentrace.apicontroller.apimodel.SensorResponse;
import se.cag.labs.currentrace.services.repository.datamodel.SensorModel;

import java.util.Date;

public final class SensorMapper {
  public static SensorModel createModelFromApi(Sensor sensor) {
    if (StringUtils.isNotBlank(sensor.getSensorId()) && StringUtils.length(sensor.getSensorId()) < 128) {
      if (StringUtils.isNotBlank(sensor.getSensorIpAddress()) && StringUtils.length(sensor.getSensorIpAddress()) < 16) {
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
      .registeredDate(new Date(sensorModel.getRegisteredTimestamp()))
      .build();
  }

}
