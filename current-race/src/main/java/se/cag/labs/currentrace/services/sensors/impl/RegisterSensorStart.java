package se.cag.labs.currentrace.services.sensors.impl;

import se.cag.labs.currentrace.apicontroller.apimodel.RaceStatus;
import se.cag.labs.currentrace.services.repository.datamodel.CurrentRaceStatus;
import se.cag.labs.currentrace.services.sensors.RegisterSensor;

public class RegisterSensorStart implements RegisterSensor {
  @Override
  public boolean updateStatus(CurrentRaceStatus currentRaceStatus, long timestamp) {
    if (!RaceStatus.Event.START.equals(currentRaceStatus.getEvent())) {
      currentRaceStatus.setStartTime(timestamp);
      currentRaceStatus.setLocalStartTime(System.currentTimeMillis());
      currentRaceStatus.setEvent(RaceStatus.Event.START);
      return true;
    }

    return false;
  }
}
