package se.cag.labs.currentrace.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.cag.labs.currentrace.services.repository.CurrentRaceRepository;
import se.cag.labs.currentrace.services.repository.datamodel.RaceStatus;

@Service
public class CancelRaceService {
    @Autowired
    private CurrentRaceRepository repository;
    @Autowired
    private CallbackService callbackService;

    public enum ReturnStatus {
        ACCEPTED,
        NOT_FOUND
    }

    public ReturnStatus cancelRace() {
        RaceStatus raceStatus = repository.findByRaceId(RaceStatus.ID);

        if (raceStatus != null) {
            raceStatus.setRaceActivatedTime(null);
            raceStatus.setFinishTime(null);
            raceStatus.setMiddleTime(null);
            raceStatus.setStartTime(null);
            raceStatus.setEvent(null);
            raceStatus.setState(RaceStatus.State.INACTIVE);

            repository.save(raceStatus);
            callbackService.reportStatus(raceStatus);
            return ReturnStatus.ACCEPTED;
        }

        return ReturnStatus.NOT_FOUND;
    }
}
