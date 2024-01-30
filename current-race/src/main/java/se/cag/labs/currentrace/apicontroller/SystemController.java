package se.cag.labs.currentrace.apicontroller;

import org.springframework.web.bind.annotation.*;
import se.cag.labs.currentrace.apicontroller.apimodel.VersionResponse;
import se.cag.labs.currentrace.config.BuildInfo;

@RestController
@CrossOrigin(origins = { "http://localhost:3000", "http://192.168.0.50:30080" }, methods = {RequestMethod.GET}, allowCredentials = "true")
public class SystemController {
  @RequestMapping(value = "/version", method = RequestMethod.GET)
  public VersionResponse getVersion(@RequestHeader(required = false) String authorization) {
    return new VersionResponse(BuildInfo.getVersion());
  }
}
