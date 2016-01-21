package se.cag.labs.currentrace.apicontroller.apimodel;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Builder
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonDeserialize(builder = RaceStatus.RaceStatusBuilder.class)
public final class RaceStatus {
    private final Event event;
    private final Date startTime;
    private final Date splitTime;
    private final Date finishTime;
    private final State state;

    @JsonPOJOBuilder(withPrefix = "")
    public static final class RaceStatusBuilder {
    }

    public enum Event {
        NONE, START, SPLIT, FINISH, TIME_OUT_NOT_STARTED, TIME_OUT_NOT_FINISHED, DISQUALIFIED
    }

    public enum State {
        ACTIVE, INACTIVE
    }
}