package se.cag.labs.raceadmin;

import com.fasterxml.jackson.annotation.*;
import lombok.*;
import org.springframework.data.annotation.Id;
import se.cag.labs.raceadmin.*;

@Data
@RequiredArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RaceStatus {
    public enum RaceState {
        ACTIVE, INACTIVE, UNVERIFIED
    }

    public enum RaceEvent {
        NONE,START,SPLIT,FINISH,TIME_OUT_NOT_STARTED, TIME_OUT_NOT_FINISHED,DISQUALIFIED
    }

    @Id
    @Setter(AccessLevel.PRIVATE) private String id;
    private User user;
    private RaceEvent event;
    private RaceState state;
    private Long startTime;
    private Long splitTime;
    private Long finishTime;

    public RaceStatus(User user) {
        this.user = user;
    }
}
