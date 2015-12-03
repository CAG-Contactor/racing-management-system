package se.cag.labs.currentrace.services.sensors.impl;

import se.cag.labs.currentrace.services.repository.datamodel.CurrentRaceStatus;
import se.cag.labs.currentrace.services.sensors.RegisterSensor;

public class RegisterSensorMiddle implements RegisterSensor {
    @Override
    public boolean updateStatus(CurrentRaceStatus currentRaceStatus, long timestamp) {
        if (!CurrentRaceStatus.Event.MIDDLE.equals(currentRaceStatus.getEvent())) {
            currentRaceStatus.setMiddleTime(timestamp);
            currentRaceStatus.setEvent(CurrentRaceStatus.Event.MIDDLE);
            return true;
        }
        return false;
    }
}
