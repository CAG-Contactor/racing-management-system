package se.cag.labs.currentrace.apicontroller.mapper;

import se.cag.labs.common.apimodel.RaceStatus;
import se.cag.labs.currentrace.services.repository.datamodel.CurrentRaceStatus;

import java.util.Date;

public final class ModelMapper {
    public static RaceStatus createStatusResponse(CurrentRaceStatus currentRaceStatus) {
        return new RaceStatus(currentRaceStatus.getEvent() == null ? null : RaceStatus.Event.valueOf(currentRaceStatus.getEvent().name()),
                currentRaceStatus.getStartTime() == null ? null : new Date(currentRaceStatus.getStartTime()),
                currentRaceStatus.getMiddleTime() == null ? null : new Date(currentRaceStatus.getMiddleTime()),
                currentRaceStatus.getFinishTime() == null ? null : new Date(currentRaceStatus.getFinishTime()),
                currentRaceStatus.getState() == null ? null : RaceStatus.State.valueOf(currentRaceStatus.getState().name()));
    }
}
