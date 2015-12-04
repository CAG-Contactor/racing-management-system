package se.cag.labs.currentrace.services.sensors.impl;

import se.cag.labs.currentrace.services.repository.datamodel.CurrentRaceStatus;
import se.cag.labs.currentrace.services.sensors.RegisterSensor;

public class RegisterSensorFinish implements RegisterSensor {
    @Override
    public boolean updateStatus(CurrentRaceStatus currentRaceStatus, long timestamp) {
        if (!CurrentRaceStatus.Event.FINISH.equals(currentRaceStatus.getEvent())) {
            currentRaceStatus.setFinishTime(timestamp);
            CurrentRaceStatus.Event event = currentRaceStatus.getMiddleTime() == null ? CurrentRaceStatus.Event.DISQUALIFIED : CurrentRaceStatus.Event.FINISH;
            currentRaceStatus.setEvent(event);
            currentRaceStatus.setState(CurrentRaceStatus.State.INACTIVE);
            return true;
        }
        return false;
    }
}
