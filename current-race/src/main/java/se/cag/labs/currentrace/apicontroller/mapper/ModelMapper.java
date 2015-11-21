package se.cag.labs.currentrace.apicontroller.mapper;

import se.cag.labs.currentrace.apicontroller.apimodel.StatusResponse;
import se.cag.labs.currentrace.services.repository.datamodel.RaceStatus;

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
