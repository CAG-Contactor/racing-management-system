package se.cag.labs.cagrms.clientapi.service;

import lombok.extern.log4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.context.annotation.*;
import org.springframework.http.*;
import org.springframework.stereotype.*;
import org.springframework.web.client.*;

@Component
@Scope("singleton")
@Log4j
public class ForwardingService {
    @Value("${server.usermanager.base.uri}")
    private String userManagerBaseUri;

    private RestTemplate restTemplate = new RestTemplate();

    public ResponseEntity<Void> registerUser(User user) {
        return restTemplate.exchange(userManagerBaseUri + "/users", HttpMethod.POST, new HttpEntity<>(user), Void.class);
    }
}
