package se.cag.labs.currentrace.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.cag.labs.currentrace.apicontroller.apimodel.RaceStatus;
import se.cag.labs.currentrace.services.repository.CurrentRaceRepository;
import se.cag.labs.currentrace.services.repository.datamodel.CurrentRaceStatus;

@Service
public class CancelRaceService {
  @Autowired
  private CurrentRaceRepository repository;
  @Autowired
  private CallbackService callbackService;

  public ReturnStatus cancelRace() {
    CurrentRaceStatus currentRaceStatus = repository.findByRaceId(CurrentRaceStatus.ID);

    if (currentRaceStatus != null) {
      currentRaceStatus.setRaceActivatedTime(null);
      currentRaceStatus.setFinishTime(null);
      currentRaceStatus.setSplitTime(null);
      currentRaceStatus.setStartTime(null);
      currentRaceStatus.setEvent(null);
      currentRaceStatus.setState(RaceStatus.State.INACTIVE);

      repository.save(currentRaceStatus);
      callbackService.reportStatus(currentRaceStatus);
      return ReturnStatus.ACCEPTED;
    }

    return ReturnStatus.NOT_FOUND;
  }

  public enum ReturnStatus {
    ACCEPTED,
    NOT_FOUND
  }
}
