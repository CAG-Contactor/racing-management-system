/*
 * User: joel
 * Date: 2015-12-02
 * Time: 19:56
 */
package se.cag.labs.currentrace.services;

import lombok.extern.java.*;
import org.springframework.stereotype.*;
import org.springframework.web.client.*;
import se.cag.labs.currentrace.apicontroller.mapper.ModelMapper;
import se.cag.labs.currentrace.services.repository.datamodel.*;

@Component
@Log
public class CallbackService {
    private RestTemplate restTemplate = new RestTemplate();

    public void reportStatus(RaceStatus status) {
        log.fine("Report status:"+status);
        restTemplate.postForLocation(status.getCallbackUrl(), ModelMapper.createStatusResponse(status));
    }
}
