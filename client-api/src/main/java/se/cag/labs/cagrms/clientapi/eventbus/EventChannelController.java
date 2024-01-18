package se.cag.labs.cagrms.clientapi.eventbus;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.InputStream;

@RestController
@CrossOrigin(origins = "http://localhost:3000", methods = {RequestMethod.GET, RequestMethod.POST}, allowCredentials = "true")
@Slf4j
public class EventChannelController {
  @Autowired
  private EventChannelSocketHandler eventChannelSocketHandler;
  @Autowired
  private ObjectMapper objectMapper;

  @RequestMapping(value = "/event", method = RequestMethod.POST, consumes = {"application/json"})
  public ResponseEntity sendEvent(InputStream stream) {

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
