package se.cag.labs.raceadmin.peerservices;


import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.web.client.*;
import org.springframework.web.util.*;
import se.cag.labs.raceadmin.*;

@Service
public class CurrentRaceService {

  @Value("${server.currentrace.base.uri}")
  private String currentRaceBaseUri;
  @Value("${server.address}")
  private String selfAddress;
  @Value("${server.port}")
  private String selfPort;

  private RestTemplate restTemplate = new RestTemplate();

  public void startRace() {
    final UriComponents uri = UriComponentsBuilder
      .fromHttpUrl(currentRaceBaseUri + "/startRace")
      .queryParam("callbackUrl", "http://" + selfAddress + ":" + selfPort + "/on-race-status-update")
      .build();
    restTemplate.postForObject(uri.toUri(), null, Void.class);
  }
  public void cancelRace() {
    final UriComponents uri = UriComponentsBuilder
      .fromHttpUrl(currentRaceBaseUri + "/cancelRace")
      .build();
    restTemplate.postForObject(uri.toUri(), null, Void.class);
  }
  public RaceStatus status() {
    final UriComponents uri = UriComponentsBuilder
      .fromHttpUrl(currentRaceBaseUri + "/status")
      .build();
    return restTemplate.getForObject(uri.toUri(), RaceStatus.class);
  }

}
