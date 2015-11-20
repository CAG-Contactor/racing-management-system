package se.cag.labs.currentrace.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import se.cag.labs.currentrace.CurrentRaceRepository;

@Service
public class PassageDetectedService {
    @Autowired
    private CurrentRaceRepository repository;

    public ResponseEntity passageDetected(String sensorID, long timestamp) {
        System.out.println("passageDetected on : " + sensorID + " at: " + timestamp);
        return new ResponseEntity(HttpStatus.ACCEPTED);
    }
}
