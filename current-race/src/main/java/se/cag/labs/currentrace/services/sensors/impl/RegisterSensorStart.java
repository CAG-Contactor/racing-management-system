package se.cag.labs.currentrace.services.sensors.impl;

import se.cag.labs.currentrace.services.RegisterSensor;
import se.cag.labs.currentrace.services.repository.datamodel.RaceStatus;

public class RegisterSensorStart implements RegisterSensor {
    @Override
    public boolean updateStatus(RaceStatus raceStatus, long timestamp) {
        if (!RaceStatus.Event.START.equals(raceStatus.getEvent())) {
            raceStatus.setStartTime(timestamp);
            raceStatus.setEvent(RaceStatus.Event.START);
            return true;
        }

        return false;
    }
}
