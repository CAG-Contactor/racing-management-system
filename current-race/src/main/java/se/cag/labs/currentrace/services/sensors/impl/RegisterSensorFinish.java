package se.cag.labs.currentrace.services.sensors.impl;

import se.cag.labs.currentrace.apicontroller.apimodel.RaceStatus;
import se.cag.labs.currentrace.services.repository.datamodel.CurrentRaceStatus;
import se.cag.labs.currentrace.services.sensors.RegisterSensor;

public class RegisterSensorFinish implements RegisterSensor {
    @Override
    public boolean updateStatus(CurrentRaceStatus currentRaceStatus, long timestamp) {
        if (!RaceStatus.Event.FINISH.equals(currentRaceStatus.getEvent())) {
            currentRaceStatus.setFinishTime(timestamp);
            RaceStatus.Event event = currentRaceStatus.getSplitTime() == null ? RaceStatus.Event.DISQUALIFIED : RaceStatus.Event.FINISH;
            currentRaceStatus.setEvent(event);
            currentRaceStatus.setState(RaceStatus.State.INACTIVE);
            return true;
        }
        return false;
    }
}
