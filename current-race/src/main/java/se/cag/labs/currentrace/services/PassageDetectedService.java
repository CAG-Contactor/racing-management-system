package se.cag.labs.currentrace.services;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import se.cag.labs.currentrace.apicontroller.apimodel.*;
import se.cag.labs.currentrace.services.repository.*;
import se.cag.labs.currentrace.services.repository.datamodel.*;
import se.cag.labs.currentrace.services.sensors.*;


@Service
public class PassageDetectedService {
  @Autowired
  private CurrentRaceRepository repository;
  @Autowired
  private CallbackService callbackService;

  public ReturnStatus passageDetected(String sensorID, long timestamp) {
    CurrentRaceStatus currentRaceStatus = repository.findByRaceId(CurrentRaceStatus.ID);

    RegisterSensorType registerSensorType = RegisterSensorType.get(sensorID);
    if (registerSensorType == null) {
      return ReturnStatus.ERROR;
    }

    if (currentRaceStatus != null && RaceStatus.State.ACTIVE.equals(currentRaceStatus.getState())) {
      RegisterSensor sensor = RegisterSensorFactory.INSTANCE.createRegisterSensorObject(registerSensorType);
      if (sensor.updateStatus(currentRaceStatus, timestamp)) {
        repository.save(currentRaceStatus);
        callbackService.reportStatus(currentRaceStatus);
        return ReturnStatus.ACCEPTED;
      }
    }

    return ReturnStatus.IGNORED;
  }

  public enum ReturnStatus {
    ACCEPTED,
    IGNORED,
    ERROR
  }
}
