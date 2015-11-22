package se.cag.labs.currentrace.services.sensors.impl;

import se.cag.labs.currentrace.services.RegisterSensor;
import se.cag.labs.currentrace.services.repository.datamodel.RaceStatus;

public class RegisterSensorFinish implements RegisterSensor {
    @Override
    public boolean updateStatus(RaceStatus raceStatus, long timestamp) {
        if (!RaceStatus.Event.FINISH.equals(raceStatus.getEvent())) {
            raceStatus.setFinishTime(timestamp);
            RaceStatus.Event event = raceStatus.getMiddleTime() == null ? RaceStatus.Event.DISQUALIFIED : RaceStatus.Event.FINISH;
            raceStatus.setEvent(event);
            raceStatus.setState(RaceStatus.State.INACTIVE);
            return true;
        }
        return false;
    }
}
