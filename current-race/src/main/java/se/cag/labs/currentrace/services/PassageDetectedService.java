package se.cag.labs.currentrace.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.cag.labs.currentrace.CurrentRaceRepository;

@Service
public class PassageDetectedService {
    @Autowired
    private CurrentRaceRepository repository;

    public String passageDetected(String sensorID, long timestamp) {
        return "passageDetected on : " + sensorID + " at: " + timestamp;
    }
}
