package se.cag.labs.currentrace.apicontroller.mapper;

import se.cag.labs.currentrace.apicontroller.apimodel.RaceStatus;
import se.cag.labs.currentrace.services.repository.datamodel.CurrentRaceStatus;

public final class ModelMapper {
  private ModelMapper() {
    //Not used
  }

  public static RaceStatus createStatusResponse(CurrentRaceStatus currentRaceStatus) {
    if (currentRaceStatus == null) {
      return RaceStatus.builder()
        .state(RaceStatus.State.INACTIVE)
        .build();
    }

    return RaceStatus.builder()
      .event(currentRaceStatus.getEvent() == null ? null : currentRaceStatus.getEvent())
      .startTime(currentRaceStatus.getStartTime())
      .splitTime(currentRaceStatus.getSplitTime())
      .currentTime(currentRaceStatus.getLocalStartTime() == null ? null : System.currentTimeMillis() - currentRaceStatus.getLocalStartTime())
      .finishTime(currentRaceStatus.getFinishTime())
      .state(currentRaceStatus.getState() == null ? null : currentRaceStatus.getState())
      .build();
  }
}
