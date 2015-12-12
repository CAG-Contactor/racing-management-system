package se.cag.labs.cagrms.clientapi.eventbus;

import io.swagger.annotations.*;
import lombok.extern.log4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.io.*;

@Api(basePath = "*",
        value = "Client API",
        description = "The back-end service facade for the web client of the CAG Racing Management System",
        produces = "application/json"
)
@RestController
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST})
@Log4j
public class EventBusController {
    @Autowired
    private EventBusSocketHandler eventBus;

    @RequestMapping(value = "/event", method = RequestMethod.POST, consumes = "application/octet-stream")
    @ApiOperation(value = "Send an event via the event bus",
            notes = "Send an event via the event bus")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "The event was sent successfully"),
            @ApiResponse(code = 500, message = "Something went wrong when sending the event")
    })
    public ResponseEntity startRace(
            @ApiParam(value = "The message to send",
                    defaultValue = "Empty string",
                    required = false)
            InputStream stream) {

        StringBuilder sb = new StringBuilder();
        final LineNumberReader reader = new LineNumberReader(new InputStreamReader(stream));
        String line;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line).append('\n');
            }
        } catch (IOException e) {
            log.warn("Failed to read message", e);
        }

        eventBus.broadcastMessage(sb.toString());
        return ResponseEntity.ok().build();
    }
}
