package se.cag.labs.currentrace.services.sensors.impl;

import se.cag.labs.currentrace.apicontroller.apimodel.*;
import se.cag.labs.currentrace.services.repository.datamodel.*;
import se.cag.labs.currentrace.services.sensors.*;

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
