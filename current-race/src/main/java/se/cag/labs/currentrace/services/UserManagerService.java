package se.cag.labs.currentrace.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
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
@Scope("singleton")
@Slf4j
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
      log.error("Rest call failed", e);
    }

    return new ArrayList<>();
  }

}
