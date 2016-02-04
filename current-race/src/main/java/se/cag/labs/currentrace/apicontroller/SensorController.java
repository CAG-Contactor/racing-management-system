package se.cag.labs.currentrace.apicontroller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import se.cag.labs.currentrace.apicontroller.apimodel.Sensor;
import se.cag.labs.currentrace.apicontroller.apimodel.SensorResponse;
import se.cag.labs.currentrace.services.SensorService;

import java.util.List;
@RestController
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST})
public class SensorController {

  @Autowired
  private SensorService sensorService;

  @RequestMapping(value = "/sensors", method = RequestMethod.POST)
  @ApiOperation(value = "Register sensor IP-adresses")
  public void registerSensor(
    @ApiParam(defaultValue = "{\"id\":\"sensorid\", \"ip\":\"192.168.1.1\"}")
    @RequestBody Sensor sensor) {
    sensorService.registerSensor(sensor);
  }

  @RequestMapping(value = "/sensors", method = RequestMethod.GET)
  @ApiOperation(value = "Get registered sensors")
  public List<SensorResponse> getRegisteredSensors() {
    return sensorService.getSensorList();
  }

}
