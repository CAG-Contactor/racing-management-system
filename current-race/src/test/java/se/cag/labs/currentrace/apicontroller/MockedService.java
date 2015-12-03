package se.cag.labs.currentrace.apicontroller;

import org.springframework.web.bind.annotation.*;
import se.cag.labs.common.apimodel.RaceStatus;

@RestController
@CrossOrigin(origins = "*", methods = {RequestMethod.POST})
public class MockedService {
    @RequestMapping(value = "/onracestatusupdate", method = RequestMethod.POST)
    public void onRaceStatusUpdate(@RequestBody RaceStatus status) {
    }
}
