package se.cag.labs.currentrace.apicontroller;

import lombok.extern.java.Log;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import se.cag.labs.common.apimodel.RaceStatus;

@RestController
@CrossOrigin(origins = "*", methods = {RequestMethod.POST})
@Log
public class MockedService {
    @RequestMapping(value = "/onracestatusupdate", method = RequestMethod.POST)
    public void onRaceStatusUpdate(@RequestBody RaceStatus status) {
        log.entering(MockedService.class.getName(), "onRaceStatusUpdate");
        log.info(String.valueOf(status));
    }
}
