package se.cag.labs.raceadmin.services;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class LeaderBoardService {

    @Value("${server.leaderboard.base.uri}")
    private String baseUri;

    public void newResult(UserResult userResult) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.postForObject(baseUri + "/results", userResult, Void.class);
    }
}
