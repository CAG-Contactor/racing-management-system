package se.cag.labs.currentrace.apicontroller.apimodel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Sensor {
  private String id;
  private String ip;
}
