package se.cag.labs.currentrace.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.cag.labs.currentrace.services.repository.CurrentRaceRepository;
import se.cag.labs.currentrace.services.repository.datamodel.CurrentRaceStatus;
import se.cag.labs.currentrace.services.sensors.RegisterSensor;
import se.cag.labs.currentrace.services.sensors.RegisterSensorFactory;
import se.cag.labs.currentrace.services.sensors.RegisterSensorType;


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
        CurrentRaceStatus currentRaceStatus = repository.findByRaceId(CurrentRaceStatus.ID);

        RegisterSensorType registerSensorType = RegisterSensorType.get(sensorID);
        if (registerSensorType == null) {
            return ReturnStatus.ERROR;
        }

        if (currentRaceStatus != null && CurrentRaceStatus.State.ACTIVE.equals(currentRaceStatus.getState())) {
            RegisterSensor sensor = RegisterSensorFactory.INSTANCE.createRegisterSensorObject(registerSensorType);
            if (sensor.updateStatus(currentRaceStatus, timestamp)) {
                repository.save(currentRaceStatus);
                callbackService.reportStatus(currentRaceStatus);
                return ReturnStatus.ACCEPTED;
            }
        }

        return ReturnStatus.IGNORED;
    }
}
