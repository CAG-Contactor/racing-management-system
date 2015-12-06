package se.cag.labs.currentrace.services;

import lombok.extern.log4j.*;
import org.springframework.stereotype.*;
import org.springframework.web.client.*;
import se.cag.labs.currentrace.apicontroller.mapper.*;
import se.cag.labs.currentrace.services.repository.datamodel.*;

@Component
@Log4j
public class CallbackService {
    private RestTemplate restTemplate = new RestTemplate();

    public void reportStatus(CurrentRaceStatus status) {
        log.fine("Report status:" + status);
        try {
            restTemplate.postForLocation(status.getCallbackUrl(), ModelMapper.createStatusResponse(status));
        } catch (RestClientException e) {
            log.warning("Rest call failed: " + e.getLocalizedMessage());
        }
    }
}
