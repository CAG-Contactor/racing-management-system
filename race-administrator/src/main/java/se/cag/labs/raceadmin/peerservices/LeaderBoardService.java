package se.cag.labs.raceadmin.peerservices;


import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.web.client.*;
import se.cag.labs.raceadmin.*;

@Service
public class LeaderBoardService {

  @Value("${server.leaderboard.base.uri}")
  private String baseUri;

  private RestTemplate restTemplate = new RestTemplate();

  public void newResult(UserResult userResult) {
    restTemplate.postForObject(baseUri + "/results", userResult, Void.class);
  }
}
