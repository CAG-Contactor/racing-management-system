package se.cag.labs.currentrace.services;

import org.springframework.stereotype.Service;

@Service
public class PingService {

    public String ping() {
        return "pong!";
    }
}
