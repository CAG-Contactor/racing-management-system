package se.cag.labs.currentrace.services;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import se.cag.labs.currentrace.services.repository.*;
import se.cag.labs.currentrace.services.repository.datamodel.*;
import se.cag.labs.currentrace.services.sensors.*;


@Service
public class PassageDetectedService {
    @Autowired
    private CurrentRaceRepository repository;
    @Autowired
    private CallbackService callbackService;

    public enum ReturnStatus {
        ACCEPTED,
        IGNORED,
        ERROR
    }

    public ReturnStatus passageDetected(String sensorID, long timestamp) {
        RaceStatus raceStatus = repository.findByRaceId(RaceStatus.ID);

        RegisterSensorType registerSensorType = RegisterSensorType.get(sensorID);
        if (registerSensorType == null) {
            return ReturnStatus.ERROR;
        }

        if (raceStatus != null && RaceStatus.State.ACTIVE.equals(raceStatus.getState())) {
            RegisterSensor sensor = RegisterSensorFactory.INSTANCE.createRegisterSensorObject(registerSensorType);
            if (sensor.updateStatus(raceStatus, timestamp)) {
                repository.save(raceStatus);
                callbackService.reportStatus(raceStatus);
                return ReturnStatus.ACCEPTED;
            }
        }

        return ReturnStatus.IGNORED;
    }
}
