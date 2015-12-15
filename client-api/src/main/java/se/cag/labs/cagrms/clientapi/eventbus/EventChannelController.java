package se.cag.labs.cagrms.clientapi.eventbus;

import com.fasterxml.jackson.databind.*;
import io.swagger.annotations.*;
import lombok.extern.log4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.io.*;

@Api(basePath = "*",
        value = "Client Event Channel",
        description = "The internal API for back-end services to be used for " +
                "sending events to the web client."
)
@RestController
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST})
@Log4j
public class EventChannelController {
    @Autowired
    private EventChannelSocketHandler eventChannelSocketHandler;
    @Autowired
    private ObjectMapper objectMapper;

    @RequestMapping(value = "/event", method = RequestMethod.POST, consumes = {"application/json"})
    @ApiOperation(value = "Send an event to the client, via the event bus",
            notes = "Send an event to the client, via the event bus.<br>"+
                    "The event shall be a JSON object with at least one field:<br>"+
                    "<pre>\n  {\n    \"eventName\":&lt;string&gt;\n  }\n</pre>")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "The event object was sent successfully"),
            @ApiResponse(code = 400, message = "The event object was not a JSON object containing \"eventType\""),
            @ApiResponse(code = 500, message = "Something went wrong when sending the event")
    })
    public ResponseEntity startRace(
            @ApiParam(value = "The message to send",
                    defaultValue = "{\"test\":42}",
                    required = false)
            InputStream stream) {

        try {
            final JsonNode jsonTree = objectMapper.readTree(stream);
            if (!jsonTree.has("eventType")) {
                return ResponseEntity.badRequest().build();
            }
            eventChannelSocketHandler.broadcastMessage(jsonTree.toString());
            return ResponseEntity.ok().build();
        } catch (IOException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
