package se.cag.labs.currentrace.apicontroller.apimodel;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Builder
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonDeserialize(builder = SensorResponse.SensorResponseBuilder.class)
public class SensorResponse {
  private String sensorId;
  private String sensorIpAddress;
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Europe/Stockholm")
  private LocalDateTime registeredDate;

  @JsonPOJOBuilder(withPrefix = "")
  public static final class SensorResponseBuilder {
  }
}
