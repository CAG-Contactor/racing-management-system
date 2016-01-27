package se.cag.labs.currentrace.apicontroller.apimodel;


import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.*;
import lombok.*;

import java.util.*;

@Builder
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonDeserialize(builder = RaceStatus.RaceStatusBuilder.class)
public final class RaceStatus {
  private final Event event;
  private final Date startTime;
  private final Date splitTime;
  private final Date finishTime;
  private final Date currentTime;
  private final State state;

  public enum Event {
    NONE, START, SPLIT, FINISH, TIME_OUT_NOT_STARTED, TIME_OUT_NOT_FINISHED, DISQUALIFIED
  }

  public enum State {
    ACTIVE, INACTIVE
  }

  @JsonPOJOBuilder(withPrefix = "")
  public static final class RaceStatusBuilder {
  }
}