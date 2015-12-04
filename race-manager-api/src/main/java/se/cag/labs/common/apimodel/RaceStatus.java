package se.cag.labs.common.apimodel;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Builder;

import java.util.Date;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonDeserialize(builder = RaceStatus.RaceStatusBuilder.class)
public class RaceStatus {
    @JsonProperty
    private Event event;
    @JsonProperty
    private Date startTime;
    @JsonProperty
    private Date middleTime;
    @JsonProperty
    private Date finishTime;
    @JsonProperty
    private State state;

    public enum Event {
        NONE, START, MIDDLE, FINISH, TIME_OUT_NOT_STARTED, TIME_OUT_NOT_FINISHED, DISQUALIFIED
    }

    public enum State {
        ACTIVE, INACTIVE
    }


}
