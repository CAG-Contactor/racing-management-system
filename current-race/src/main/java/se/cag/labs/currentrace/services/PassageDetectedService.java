package se.cag.labs.currentrace.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.cag.labs.currentrace.services.repository.CurrentRaceRepository;
import se.cag.labs.currentrace.services.repository.datamodel.RaceStatus;
import se.cag.labs.currentrace.services.sensors.RegisterSensor;
import se.cag.labs.currentrace.services.sensors.RegisterSensorFactory;
import se.cag.labs.currentrace.services.sensors.SensorType;


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

        SensorType sensorType = SensorType.get(sensorID);
        if (sensorType == null) {
            return ReturnStatus.ERROR;
        }

        if (raceStatus != null && RaceStatus.State.ACTIVE.equals(raceStatus.getState())) {
            RegisterSensor sensor = RegisterSensorFactory.INSTANCE.createRegisterSensorObject(sensorType);
            if (sensor.updateStatus(raceStatus, timestamp)) {
                repository.save(raceStatus);
                return ReturnStatus.ACCEPTED;
            }
        }

        return ReturnStatus.IGNORED;
    }
}
