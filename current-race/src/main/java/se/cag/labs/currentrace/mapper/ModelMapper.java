package se.cag.labs.currentrace.mapper;

import se.cag.labs.currentrace.apimodel.StatusResponse;
import se.cag.labs.currentrace.datamodel.RaceStatus;

import java.util.Date;

public final class ModelMapper {
    public static StatusResponse createStatusResponse(RaceStatus raceStatus) {
        return new StatusResponse(raceStatus.getEvent(),
                raceStatus.getStartTime() == null ? null : new Date(raceStatus.getStartTime()),
                raceStatus.getMiddleTime() == null ? null : new Date(raceStatus.getMiddleTime()),
                raceStatus.getFinishTime() == null ? null : new Date(raceStatus.getFinishTime()),
                raceStatus.getState());
    }
}
