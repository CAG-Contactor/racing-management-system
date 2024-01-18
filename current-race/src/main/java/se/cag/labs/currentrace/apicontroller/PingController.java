package se.cag.labs.currentrace.apicontroller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = { "http://localhost:3000" }, methods = {RequestMethod.GET}, allowCredentials = "true")
public class PingController {

    public static final String PING_URL = "/ping";

    @RequestMapping(value = "/ping", method = RequestMethod.GET)
    public ResponseEntity ping() {

        return new ResponseEntity(HttpStatus.ACCEPTED);
    }
}
