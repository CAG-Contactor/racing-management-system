package se.cag.labs.currentrace.apicontroller.mapper;

import se.cag.labs.currentrace.apicontroller.apimodel.*;
import se.cag.labs.currentrace.services.repository.datamodel.*;

import java.util.*;

public final class ModelMapper {
  public static RaceStatus createStatusResponse(CurrentRaceStatus currentRaceStatus) {
    if (currentRaceStatus == null) {
      return RaceStatus.builder()
        .state(RaceStatus.State.INACTIVE)
        .build();
    }

    return RaceStatus.builder()
      .event(currentRaceStatus.getEvent() == null ? null : currentRaceStatus.getEvent())
      .startTime(currentRaceStatus.getStartTime() == null ? null : new Date(currentRaceStatus.getStartTime()))
      .splitTime(currentRaceStatus.getSplitTime() == null ? null : new Date(currentRaceStatus.getSplitTime()))
      .currentTime(currentRaceStatus.getRaceActivatedTime() == null ? null : new Date(System.currentTimeMillis() - currentRaceStatus.getLocalStartTime()))
      .finishTime(currentRaceStatus.getFinishTime() == null ? null : new Date(currentRaceStatus.getFinishTime()))
      .state(currentRaceStatus.getState() == null ? null : currentRaceStatus.getState())
      .build();
  }
}
