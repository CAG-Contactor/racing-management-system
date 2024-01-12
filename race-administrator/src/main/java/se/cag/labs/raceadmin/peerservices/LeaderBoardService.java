package se.cag.labs.raceadmin.peerservices;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import se.cag.labs.raceadmin.UserResult;

@Service
public class LeaderBoardService {

  @Value("${server.leaderboard.base.uri}")
  private String baseUri;

  private RestTemplate restTemplate = new RestTemplate();

  public void newResult(UserResult userResult) {
    restTemplate.postForObject(baseUri + "/results", userResult, Void.class);
  }
}
