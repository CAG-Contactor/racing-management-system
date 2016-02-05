package se.cag.labs.currentrace.services.repository.datamodel;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class SensorModel {
  public static final String ID = "ID";
  @Id
  private String id;
  private String sensorId;
  private String sensorIpAddress;
  private Long registeredTimestamp;
}
