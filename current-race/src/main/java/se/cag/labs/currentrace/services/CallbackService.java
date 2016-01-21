package se.cag.labs.currentrace.services;

import lombok.extern.log4j.*;
import org.springframework.context.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.web.client.*;
import se.cag.labs.currentrace.apicontroller.mapper.*;
import se.cag.labs.currentrace.services.repository.datamodel.*;

@Component
@Scope("singleton")
@Log4j
public class CallbackService {
  private RestTemplate restTemplate = new RestTemplate();

  public void reportStatus(CurrentRaceStatus status) {
    log.debug("Report status:" + status);
    try {
      restTemplate.postForLocation(status.getCallbackUrl(), ModelMapper.createStatusResponse(status));
    } catch (RestClientException e) {
      log.error("Rest call failed: " + e.getLocalizedMessage());
    }
  }
}
