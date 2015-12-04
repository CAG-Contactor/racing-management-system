package se.cag.labs.currentrace.apicontroller.mapper;

import se.cag.labs.common.apimodel.RaceStatus;
import se.cag.labs.currentrace.services.repository.datamodel.CurrentRaceStatus;

import java.util.Date;

public final class ModelMapper {
    public static RaceStatus createStatusResponse(CurrentRaceStatus currentRaceStatus) {
        if (currentRaceStatus == null) {
            return RaceStatus.builder()
                    .state(RaceStatus.State.INACTIVE)
                    .build();
        }

        return RaceStatus.builder()
                .event(currentRaceStatus.getEvent() == null ? null : RaceStatus.Event.valueOf(currentRaceStatus.getEvent().name()))
                .startTime(currentRaceStatus.getStartTime() == null ? null : new Date(currentRaceStatus.getStartTime()))
                .middleTime(currentRaceStatus.getMiddleTime() == null ? null : new Date(currentRaceStatus.getMiddleTime()))
                .finishTime(currentRaceStatus.getFinishTime() == null ? null : new Date(currentRaceStatus.getFinishTime()))
                .state(currentRaceStatus.getState() == null ? null : RaceStatus.State.valueOf(currentRaceStatus.getState().name()))
                .build();
    }
}
