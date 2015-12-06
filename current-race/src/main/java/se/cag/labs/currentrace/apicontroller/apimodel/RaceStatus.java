package se.cag.labs.currentrace.apicontroller.apimodel;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.*;

import java.util.Date;

@Builder
@AllArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonDeserialize(builder = RaceStatus.RaceStatusBuilder.class)
public class RaceStatus {
    @JsonProperty
    private final Event event;
    @JsonProperty
    private final Date startTime;
    @JsonProperty
    private final Date middleTime;
    @JsonProperty
    private final Date finishTime;
    @JsonProperty
    private final State state;

    public enum Event {
        NONE, START, MIDDLE, FINISH, TIME_OUT_NOT_STARTED, TIME_OUT_NOT_FINISHED, DISQUALIFIED
    }

    public enum State {
        ACTIVE, INACTIVE
    }
}