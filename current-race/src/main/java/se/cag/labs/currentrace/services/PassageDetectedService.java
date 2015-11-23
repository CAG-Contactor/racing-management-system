package se.cag.labs.currentrace.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.cag.labs.currentrace.services.repository.CurrentRaceRepository;
import se.cag.labs.currentrace.services.repository.datamodel.RaceStatus;
import se.cag.labs.currentrace.services.sensors.RegisterSensor;
import se.cag.labs.currentrace.services.sensors.RegisterSensorFactory;
import se.cag.labs.currentrace.services.sensors.RegisterSensorType;


@Service
public class PassageDetectedService {
    @Autowired
    private CurrentRaceRepository repository;

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
                return ReturnStatus.ACCEPTED;
            }
        }

        return ReturnStatus.IGNORED;
    }
}
