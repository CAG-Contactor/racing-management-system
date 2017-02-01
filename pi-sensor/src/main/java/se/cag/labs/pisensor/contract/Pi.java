package se.cag.labs.pisensor.contract;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Pi implements Serializable {
    private String sensorId;
    private String sensorIpAddress;
}
