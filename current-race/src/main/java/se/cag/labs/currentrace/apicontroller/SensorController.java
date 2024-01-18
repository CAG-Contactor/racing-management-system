package se.cag.labs.currentrace.apicontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.cag.labs.currentrace.apicontroller.apimodel.Sensor;
import se.cag.labs.currentrace.apicontroller.apimodel.SensorResponse;
import se.cag.labs.currentrace.services.SensorService;

import java.util.List;


@RestController
@CrossOrigin(origins = { "http://localhost:3000" }, methods = {RequestMethod.GET, RequestMethod.POST}, allowCredentials = "true")
public class SensorController {
  public static final String REGISTER_SENSOR_URL = "/registerSensor";
  public static final String SHOW_REGISTERED_SENSORS_URL = "/sensors";

  @Autowired
  private SensorService sensorService;

  @RequestMapping(value = REGISTER_SENSOR_URL, method = RequestMethod.POST)
  public ResponseEntity registerSensor(
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
  public void deleteOldSensors() {
    sensorService.deleteOldSensors();
  }
  
  @RequestMapping(value = SHOW_REGISTERED_SENSORS_URL, method = RequestMethod.GET)
  public List<SensorResponse> getRegisteredSensors() {
    return sensorService.getSensorList();
  }

}
