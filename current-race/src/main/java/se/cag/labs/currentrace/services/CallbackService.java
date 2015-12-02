/*
 * User: joel
 * Date: 2015-12-02
 * Time: 19:56
 */
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

    public void reportStatus(RaceStatus status) {
        log.debug("Report status:"+status);
        restTemplate.postForLocation(status.getCallbackUrl(), ModelMapper.createStatusResponse(status));
    }
}
