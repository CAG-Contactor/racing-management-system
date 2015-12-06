package se.cag.labs.raceadmin.services;


import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.web.client.*;

import java.util.*;

@Service
public class CurrentRaceService {

    @Value("${server.currentrace.base.uri}")
    private String currentRaceBaseUri;
    @Value("${server.self.base.uri}")
    private String selfBaseUri;

    public void startRace() {
        RestTemplate restTemplate = new RestTemplate();
        Map<String, String> urlVariables = new HashMap<>();
        urlVariables.put("callbackUrl", selfBaseUri+"/onracestatusupdate");
        restTemplate.postForLocation(currentRaceBaseUri + "/startRace", null, urlVariables);
    }
}
