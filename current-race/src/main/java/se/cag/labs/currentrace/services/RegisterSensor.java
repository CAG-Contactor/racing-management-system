package se.cag.labs.currentrace.services;

import se.cag.labs.currentrace.services.repository.datamodel.RaceStatus;

public interface RegisterSensor {
    boolean updateStatus(RaceStatus raceStatus, long timestamp);
}
