package se.cag.labs.cagrms.clientapi.eventbus;

import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@Api(basePath = "*",
        value = "Client API",
        description = "The API for the CAG Racing Management System Client",
        produces = "application/json"
)
@RestController
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST})
public class EventBusController {
    @Autowired
    private EventBusSocketHandler eventBus;

    @RequestMapping(value = "/event", method = RequestMethod.POST)
    @ApiOperation(value = "Send an event via the event bus",
            notes = "Send an event via the event bus")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "The event was sent successfully"),
            @ApiResponse(code = 500, message = "Something went wrong when sending the event")
    })
    public ResponseEntity startRace(
            @RequestParam
            @ApiParam(value = "The message to send",
                    defaultValue = "Empty string",
                    required = false)
            String message) {

        eventBus.broadcastMessage(message);
        return ResponseEntity.ok().build();
    }
}
