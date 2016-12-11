/*
 * User: joel
 * Date: 2016-01-21
 * Time: 20:33
 */
package se.cag.labs.raceadmin.peerservices;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.*;
import org.springframework.web.client.*;
import org.springframework.web.util.*;

@Service
public class ClientApiService {
  @Value("${server.clientapi.base.uri}")
  private String clientApiBaseUri;

  private RestTemplate restTemplate = new RestTemplate();

  public void sendEvent(Event o) {
    final UriComponents uri = UriComponentsBuilder
      .fromHttpUrl(clientApiBaseUri + "/event")
      .build();
    restTemplate.postForObject(uri.toUri(), o, Void.class);
  }

}
