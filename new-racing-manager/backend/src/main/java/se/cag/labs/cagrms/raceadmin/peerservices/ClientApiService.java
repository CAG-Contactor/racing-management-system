/*
 * User: joel
 * Date: 2016-01-21
 * Time: 20:33
 */
package se.cag.labs.cagrms.raceadmin.peerservices;

import org.springframework.beans.factory.annotation.Value;

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
