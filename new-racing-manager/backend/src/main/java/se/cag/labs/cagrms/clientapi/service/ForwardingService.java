package se.cag.labs.cagrms.clientapi.service;

import lombok.extern.log4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.context.annotation.*;
import org.springframework.core.*;
import org.springframework.http.*;
import org.springframework.stereotype.*;
import org.springframework.web.client.*;
import org.springframework.web.util.*;

import java.net.*;
import java.util.*;

@Component
@Scope("singleton")
@Slf4j
public class ForwardingService {
  @Value("${server.usermanager.base.uri}")
  private String userManagerBaseUri;
  @Value("${server.leaderboard.base.uri}")
  private String leaderBoardBaseUri;
  @Value("${server.currentrace.base.uri}")
  private String currentRaceBaseUri;
  @Value("${server.raceadmin.base.uri}")
  private String raceAdminBaseUri;

  private RestTemplate restTemplate = new RestTemplate();

  public ResponseEntity<Void> registerUser(User user) {
    try {
      return restTemplate.exchange(userManagerBaseUri + "/users", HttpMethod.POST, new HttpEntity<>(user), Void.class);
    } catch (HttpStatusCodeException e) {
      return ResponseEntity.status(e.getStatusCode()).build();
    }
  }

  public ResponseEntity<User> login(final User user) {
    try {
      final ResponseEntity<User> response = restTemplate.exchange(userManagerBaseUri + "/login", HttpMethod.POST, new HttpEntity<>(user), User.class);
      response.getBody().setPassword(null);
      return response;
    } catch (HttpStatusCodeException e) {
      return ResponseEntity.status(e.getStatusCode()).body(null);
    }
  }

  public ResponseEntity<String> registerAndCreateToken(final User user) {
    try {
      final ResponseEntity<String> response = restTemplate.exchange(userManagerBaseUri + "/registerWithQRCode", HttpMethod.POST, new HttpEntity<>(user), String.class);
      return response;
    } catch (HttpStatusCodeException e) {
      return ResponseEntity.status(e.getStatusCode()).body(null);
    }
  }

  public ResponseEntity<Void> logout(final String token) {
    final URI uri = UriComponentsBuilder
      .fromHttpUrl(userManagerBaseUri + "/logout")
      .queryParam("token", token)
      .build()
      .toUri();
    try {
      final ResponseEntity<Void> response = restTemplate.exchange(uri, HttpMethod.POST, null, Void.class);
      return response;
    } catch (HttpStatusCodeException e) {
      return ResponseEntity.status(e.getStatusCode()).build();
    }
  }

  public ResponseEntity<List<UserResult>> getResults() {
    final URI uri = UriComponentsBuilder
      .fromHttpUrl(leaderBoardBaseUri + "/results")
      .build()
      .toUri();
    try {
      final ResponseEntity<List<UserResult>> response = restTemplate.exchange(uri, HttpMethod.GET, null, new ParameterizedTypeReference<List<UserResult>>() {
      });
      return response;
    } catch (HttpStatusCodeException e) {
      return ResponseEntity.status(e.getStatusCode()).body(null);
    }
  }

  public ResponseEntity<List<UserResult>> getResults(final User user) {
    final URI uri = UriComponentsBuilder
            .fromHttpUrl(leaderBoardBaseUri + "/resultsBy")
            .build()
            .toUri();
    try {
      final ResponseEntity<List<UserResult>> response = restTemplate.exchange(uri, HttpMethod.POST, new HttpEntity<>(user), new ParameterizedTypeReference<List<UserResult>>() {
      });
      return response;
    } catch (HttpStatusCodeException e) {
      return ResponseEntity.status(e.getStatusCode()).body(null);
    }
  }

  public ResponseEntity<List<User>> getQueue() {
    final URI uri = UriComponentsBuilder
      .fromHttpUrl(raceAdminBaseUri + "/userqueue")
      .build()
      .toUri();
    try {
      final ResponseEntity<List<User>> response = restTemplate.exchange(uri, HttpMethod.GET, null, new ParameterizedTypeReference<List<User>>() {
      });
      return response;
    } catch (HttpStatusCodeException e) {
      return ResponseEntity.status(e.getStatusCode()).body(null);
    }
  }

  public ResponseEntity<Void> registerForRace(final User user) {
    final URI uri = UriComponentsBuilder
      .fromHttpUrl(raceAdminBaseUri + "/userqueue")
      .build()
      .toUri();
    try {
      final ResponseEntity<Void> response = restTemplate.exchange(uri, HttpMethod.POST, new HttpEntity<>(user), Void.class);
      return response;
    } catch (HttpStatusCodeException e) {
      return ResponseEntity.status(e.getStatusCode()).body(null);
    }
  }

  public ResponseEntity<Void> unregisterFromRace(final User user) {
    final URI uri = UriComponentsBuilder
      .fromHttpUrl(raceAdminBaseUri + "/userqueue")
      .build()
      .toUri();
    try {
      final ResponseEntity<Void> response = restTemplate.exchange(uri, HttpMethod.DELETE, new HttpEntity<>(user), Void.class);
      return response;
    } catch (HttpStatusCodeException e) {
      return ResponseEntity.status(e.getStatusCode()).body(null);
    }
  }

  public ResponseEntity<RaceStatus> getStatus() {
    final URI uri = UriComponentsBuilder
            .fromHttpUrl(raceAdminBaseUri + "/currentrace")
            .build()
            .toUri();
    try {
      final ResponseEntity<RaceStatus> response = restTemplate.exchange(uri, HttpMethod.GET, null, new ParameterizedTypeReference<RaceStatus>() {
      });
      return response;
    } catch (HttpStatusCodeException e) {
      return ResponseEntity.status(e.getStatusCode()).body(null);
    }
  }

  public ResponseEntity<RaceStatus> getLastStatus() {
    final URI uri = UriComponentsBuilder
            .fromHttpUrl(raceAdminBaseUri + "/lastrace")
            .build()
            .toUri();
    try {
      final ResponseEntity<RaceStatus> response = restTemplate.exchange(uri, HttpMethod.GET, null, new ParameterizedTypeReference<RaceStatus>() {
      });
      return response;
    } catch (HttpStatusCodeException e) {
      return ResponseEntity.status(e.getStatusCode()).body(null);
    }
  }
  public ResponseEntity<Void> resetRace() {
    final URI uri = UriComponentsBuilder
      .fromHttpUrl(raceAdminBaseUri + "/reset-race")
      .build()
      .toUri();
    try {
      final ResponseEntity<Void> response = restTemplate.exchange(uri, HttpMethod.POST, null, new ParameterizedTypeReference<Void>() {
      });
      return response;
    } catch (HttpStatusCodeException e) {
      return ResponseEntity.status(e.getStatusCode()).body(null);
    }
  }

  public ResponseEntity<User> getUserForToken(String token) {
    try {
      final URI uri = UriComponentsBuilder
        .fromHttpUrl(userManagerBaseUri + "/users")
        .queryParam("token", token)
        .build()
        .toUri();
      return restTemplate.exchange(uri, HttpMethod.GET, null, User.class);
    } catch (HttpStatusCodeException e) {
      return ResponseEntity.status(e.getStatusCode()).body(null);
    }
  }
}
