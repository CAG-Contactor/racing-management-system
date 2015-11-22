package se.cag.labs.currentrace.services;

import se.cag.labs.currentrace.services.repository.datamodel.RaceStatus;

public class RegisterSensorMiddle implements RegisterSensor {
    @Override
    public boolean updateStatus(RaceStatus raceStatus, long timestamp) {
        if (!RaceStatus.Event.MIDDLE.equals(raceStatus.getEvent())) {
            raceStatus.setMiddleTime(timestamp);
            raceStatus.setEvent(RaceStatus.Event.MIDDLE);
            return true;
        }
        return false;
    }
}
