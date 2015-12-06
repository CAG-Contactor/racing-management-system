package se.cag.labs.currentrace.services;

import lombok.extern.java.Log;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import se.cag.labs.currentrace.apicontroller.apimodel.User;

import java.util.ArrayList;
import java.util.List;

@Component
@Log
public class UserManagerService {
    private final static String URI = "http://localhost:10280";
    private RestTemplate restTemplate = new RestTemplate();

    public List<User> getUsers() {
        ParameterizedTypeReference<List<User>> user = new ParameterizedTypeReference<List<User>>() {
        };

        ResponseEntity<List<User>> response;
        try {
            response = restTemplate.exchange(URI + "/users", HttpMethod.GET, null, user);
            return response.getBody();
        } catch (RestClientException e) {
            log.warning("Rest call failed");
        }

        return new ArrayList<>();
    }

}
