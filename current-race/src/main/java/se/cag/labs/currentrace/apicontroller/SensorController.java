package se.cag.labs.currentrace.apicontroller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.cag.labs.currentrace.apicontroller.apimodel.Sensor;
import se.cag.labs.currentrace.apicontroller.apimodel.SensorResponse;
import se.cag.labs.currentrace.services.SensorService;

import java.util.List;

@Api(basePath = "*",
  value = "Sensors Registry",
  description = "Keeps track of sensor controllers and their IP addresses",
  produces = "application/json")
@RestController
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST})
public class SensorController {
  public static final String REGISTER_SENSOR_URL = "/registerSensor";
  public static final String SHOW_REGISTERED_SENSORS_URL = "/sensors";

  @Autowired
  private SensorService sensorService;

  @RequestMapping(value = REGISTER_SENSOR_URL, method = RequestMethod.POST)
  @ApiOperation(value = "Register sensor IP-address")
  public ResponseEntity registerSensor(
    @ApiParam(defaultValue = "{\"sensorId\":\"sensor_1\", \"sensorIpAddress\":\"192.168.1.1\"}")
    @RequestBody Sensor sensor) {

    switch (sensorService.registerSensor(sensor)) {
      case ILLEGAL:
        return new ResponseEntity(HttpStatus.EXPECTATION_FAILED);
      case REGISTERED:
        return new ResponseEntity(HttpStatus.ACCEPTED);
      default:
        return new ResponseEntity(HttpStatus.I_AM_A_TEAPOT);
    }
  }

  @RequestMapping(value = SHOW_REGISTERED_SENSORS_URL, method = RequestMethod.DELETE)
  @ApiOperation(value = "Delete old registered sensors")
  public void deleteOldSensors() {
    sensorService.deleteOldSensors();
  }
  
  @RequestMapping(value = SHOW_REGISTERED_SENSORS_URL, method = RequestMethod.GET)
  @ApiOperation(value = "Get registered sensors")
  public List<SensorResponse> getRegisteredSensors() {
    return sensorService.getSensorList();
  }

}
