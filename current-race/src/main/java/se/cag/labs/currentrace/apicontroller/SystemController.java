package se.cag.labs.currentrace.apicontroller;

import org.springframework.web.bind.annotation.*;
import se.cag.labs.currentrace.apicontroller.apimodel.*;
import se.cag.labs.currentrace.config.*;

@RestController
@CrossOrigin(origins = "*", methods = {RequestMethod.GET})
public class SystemController {
  @RequestMapping(value = "/version", method = RequestMethod.GET)
  public VersionResponse getVersion(@RequestHeader(required = false) String authorization) {
    return new VersionResponse(BuildInfo.getVersion());
  }
}
