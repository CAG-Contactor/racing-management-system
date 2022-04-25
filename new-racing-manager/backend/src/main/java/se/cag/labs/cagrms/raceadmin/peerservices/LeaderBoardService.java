package se.cag.labs.cagrms.raceadmin.peerservices;


@Service
public class LeaderBoardService {

  @Value("${server.leaderboard.base.uri}")
  private String baseUri;

  private RestTemplate restTemplate = new RestTemplate();

  public void newResult(UserResult userResult) {
    restTemplate.postForObject(baseUri + "/results", userResult, Void.class);
  }
}
