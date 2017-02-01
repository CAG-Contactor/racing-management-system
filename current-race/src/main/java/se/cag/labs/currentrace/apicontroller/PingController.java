package se.cag.labs.currentrace.apicontroller;

import io.swagger.annotations.Api;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Api(basePath = "*",
        value = "Ping service",
        description = "Service that indicates if the Current Race service is up or down",
        produces = "text/plain")
@RestController
@CrossOrigin(origins = "*", methods = {RequestMethod.GET})
public class PingController {

    public static final String PING_URL = "/ping";

    @RequestMapping(value = "/ping", method = RequestMethod.GET)
    public ResponseEntity ping() {

        return new ResponseEntity(HttpStatus.ACCEPTED);
    }
}
