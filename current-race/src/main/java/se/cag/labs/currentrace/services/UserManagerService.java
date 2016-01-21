package se.cag.labs.currentrace.services;

import lombok.extern.log4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.context.annotation.*;
import org.springframework.core.*;
import org.springframework.http.*;
import org.springframework.stereotype.*;
import org.springframework.web.client.*;
import se.cag.labs.currentrace.apicontroller.apimodel.*;

import java.util.*;

@Component
@Scope("singleton")
@Log4j
public class UserManagerService {
    @Value("${server.usermanager.base.uri}")
    private String userManagerBaseUri;

    private RestTemplate restTemplate = new RestTemplate();

    public List<User> getUsers() {
        ParameterizedTypeReference<List<User>> user = new ParameterizedTypeReference<List<User>>() {
        };

        ResponseEntity<List<User>> response;
        try {
            response = restTemplate.exchange(userManagerBaseUri + "/users", HttpMethod.GET, null, user);
            return response.getBody();
        } catch (RestClientException e) {
            log.error("Rest call failed");
        }

        return new ArrayList<>();
    }

}
