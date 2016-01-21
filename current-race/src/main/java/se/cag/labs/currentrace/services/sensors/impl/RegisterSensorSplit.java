package se.cag.labs.currentrace.services.sensors.impl;

import se.cag.labs.currentrace.apicontroller.apimodel.RaceStatus;
import se.cag.labs.currentrace.services.repository.datamodel.CurrentRaceStatus;
import se.cag.labs.currentrace.services.sensors.RegisterSensor;

public class RegisterSensorSplit implements RegisterSensor {
    @Override
    public boolean updateStatus(CurrentRaceStatus currentRaceStatus, long timestamp) {
        if (!RaceStatus.Event.SPLIT.equals(currentRaceStatus.getEvent())) {
            currentRaceStatus.setSplitTime(timestamp);
            currentRaceStatus.setEvent(RaceStatus.Event.SPLIT);
            return true;
        }
        return false;
    }
}
