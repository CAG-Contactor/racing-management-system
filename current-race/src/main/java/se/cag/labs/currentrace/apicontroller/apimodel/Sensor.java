package se.cag.labs.currentrace.apicontroller.apimodel;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonDeserialize(builder = Sensor.SensorBuilder.class)
public class Sensor {
  private String sensorId;
  private String sensorIpAddress;

  @JsonPOJOBuilder(withPrefix = "")
  public static final class SensorBuilder {
  }
}
