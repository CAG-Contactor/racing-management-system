package se.cag.labs.currentrace.services.sensors;

import se.cag.labs.currentrace.services.repository.datamodel.CurrentRaceStatus;

public interface RegisterSensor {
  boolean updateStatus(CurrentRaceStatus currentRaceStatus, long timestamp);


}
