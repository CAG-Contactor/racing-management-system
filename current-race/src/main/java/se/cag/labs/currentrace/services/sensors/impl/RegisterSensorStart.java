package se.cag.labs.currentrace.services.sensors.impl;

import se.cag.labs.currentrace.services.repository.datamodel.CurrentRaceStatus;
import se.cag.labs.currentrace.services.sensors.RegisterSensor;

public class RegisterSensorStart implements RegisterSensor {
    @Override
    public boolean updateStatus(CurrentRaceStatus currentRaceStatus, long timestamp) {
        if (!CurrentRaceStatus.Event.START.equals(currentRaceStatus.getEvent())) {
            currentRaceStatus.setStartTime(timestamp);
            currentRaceStatus.setEvent(CurrentRaceStatus.Event.START);
            return true;
        }

        return false;
    }
}
