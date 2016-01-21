package se.cag.labs.currentrace.apicontroller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.log4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import se.cag.labs.currentrace.apicontroller.apimodel.RaceStatus;
import se.cag.labs.currentrace.apicontroller.mapper.ModelMapper;
import se.cag.labs.currentrace.services.CancelRaceService;
import se.cag.labs.currentrace.services.PassageDetectedService;
import se.cag.labs.currentrace.services.StartRaceService;
import se.cag.labs.currentrace.services.StatusService;

@Api(basePath = "*", value = "Current race", description = "Current race services", produces = "application/json")
@RestController
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST})
@Log4j
public class CurrentRaceController {
    public static final String START_RACE_URL = "/startRace";
    public static final String CANCEL_RACE_URL = "/cancelRace";
    public static final String PASSAGE_DETECTED_URL = "/passageDetected";
    public static final String STATUS_URL = "/status";

    @Autowired
    private StartRaceService startRaceService;
    @Autowired
    private CancelRaceService cancelRaceService;
    @Autowired
    private PassageDetectedService passageDetectedService;
    @Autowired
    private StatusService statusService;

    @RequestMapping(value = START_RACE_URL, method = RequestMethod.POST)
    @ApiOperation(value = "Start new race", notes = "Start new race")
    @ApiResponses(value = {
            @ApiResponse(code = 202, message = "Race is starting"),
            @ApiResponse(code = 302, message = "Race is already started"),
            @ApiResponse(code = 404, message = "ASD"),
            @ApiResponse(code = 418, message = "Something went terribly wrong")
    })
    public ResponseEntity startRace(
            @RequestParam
            @ApiParam(value = "The callback to use to report status changes when the race starts",
                    defaultValue = "http://localhost:10380/onracestatusupdate",
                    required = true)
            String callbackUrl) {

        switch (startRaceService.startRace(callbackUrl)) {
            case FOUND:
                return new ResponseEntity(HttpStatus.FOUND);
            case STARTED:
                return new ResponseEntity(HttpStatus.ACCEPTED);
            default:
                return new ResponseEntity(HttpStatus.I_AM_A_TEAPOT);
        }
    }

    @RequestMapping(value = CANCEL_RACE_URL, method = RequestMethod.POST)
    public ResponseEntity cancelRace() {
        switch (cancelRaceService.cancelRace()) {
            case ACCEPTED:
                return new ResponseEntity(HttpStatus.ACCEPTED);
            case NOT_FOUND:
                return new ResponseEntity(HttpStatus.NOT_FOUND);
            default:
                return new ResponseEntity(HttpStatus.I_AM_A_TEAPOT);
        }
    }

    @RequestMapping(value = PASSAGE_DETECTED_URL, method = RequestMethod.POST)
    public ResponseEntity passageDetected(@RequestParam String sensorID, @RequestParam long timestamp) {
        log.debug("/passageDetected?sensorId="+sensorID+"&timestamp="+timestamp);
        switch (passageDetectedService.passageDetected(sensorID, timestamp)) {
            case ACCEPTED:
                return new ResponseEntity(HttpStatus.ACCEPTED);
            case IGNORED:
                return new ResponseEntity(HttpStatus.ALREADY_REPORTED);
            case ERROR:
                return new ResponseEntity(HttpStatus.EXPECTATION_FAILED);
            default:
                return new ResponseEntity(HttpStatus.I_AM_A_TEAPOT);
        }
    }

    @RequestMapping(value = STATUS_URL, method = RequestMethod.GET)
    public RaceStatus status() {
        return ModelMapper.createStatusResponse(statusService.status());
    }
}
