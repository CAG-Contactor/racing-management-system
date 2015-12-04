package se.cag.labs.currentrace.apicontroller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import se.cag.labs.currentrace.apicontroller.apimodel.VersionResponse;
import se.cag.labs.currentrace.config.BuildInfo;

@RestController
@CrossOrigin(origins = "*", methods = {RequestMethod.GET})
public class SystemController {
    @RequestMapping(value = "/version", method = RequestMethod.GET)
    public VersionResponse getVersion(@RequestHeader(required = false) String authorization) {
        return new VersionResponse(BuildInfo.getVersion());
    }
}
