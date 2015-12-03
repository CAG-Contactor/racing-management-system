package se.cag.labs.common.apimodel;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.Date;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RaceStatus {
    private Event event;
    private Date startTime;
    private Date middleTime;
    private Date finishTime;
    private State state;

    public enum Event {
        NONE, START, MIDDLE, FINISH, TIME_OUT_NOT_STARTED, TIME_OUT_NOT_FINISHED, DISQUALIFIED
    }

    public enum State {
        ACTIVE, INACTIVE
    }

    public RaceStatus() {}

    public RaceStatus(Event event, Date startTime, Date middleTime, Date finishTime, State state) {
        this.event = event;
        this.startTime = startTime;
        this.middleTime = middleTime;
        this.finishTime = finishTime;
        this.state = state;
    }
}
