package se.cag.labs.currentrace.services;

import lombok.extern.log4j.Log4j;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import se.cag.labs.currentrace.apicontroller.mapper.ModelMapper;
import se.cag.labs.currentrace.services.repository.datamodel.CurrentRaceStatus;

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
      log.error("Rest call failed: " + e.getLocalizedMessage(), e);
    }
  }
}
