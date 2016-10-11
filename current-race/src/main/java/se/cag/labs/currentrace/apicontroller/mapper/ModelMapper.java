package se.cag.labs.currentrace.apicontroller.mapper;

import se.cag.labs.currentrace.apicontroller.apimodel.RaceStatus;
import se.cag.labs.currentrace.services.repository.datamodel.CurrentRaceStatus;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

public final class ModelMapper {
  public static RaceStatus createStatusResponse(CurrentRaceStatus currentRaceStatus) {
    if (currentRaceStatus == null) {
      return RaceStatus.builder()
        .state(RaceStatus.State.INACTIVE)
        .build();
    }

    return RaceStatus.builder()
      .event(currentRaceStatus.getEvent() == null ? null : currentRaceStatus.getEvent())
      .startTime(currentRaceStatus.getStartTime() == null ? null : LocalDateTime.ofInstant(Instant.ofEpochMilli(currentRaceStatus.getStartTime()), ZoneId.systemDefault()))
      .splitTime(currentRaceStatus.getSplitTime() == null ? null : LocalDateTime.ofInstant(Instant.ofEpochMilli(currentRaceStatus.getSplitTime()), ZoneId.systemDefault()))
      .currentTime(currentRaceStatus.getLocalStartTime() == null ? null : LocalDateTime.ofInstant(Instant.ofEpochMilli(System.currentTimeMillis() - currentRaceStatus.getLocalStartTime()), ZoneId.systemDefault()))
      .finishTime(currentRaceStatus.getFinishTime() == null ? null : LocalDateTime.ofInstant(Instant.ofEpochMilli(currentRaceStatus.getFinishTime()), ZoneId.systemDefault()))
      .state(currentRaceStatus.getState() == null ? null : currentRaceStatus.getState())
      .build();
  }
}
