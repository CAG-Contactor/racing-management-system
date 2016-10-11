package se.cag.labs.currentrace.apicontroller.apimodel;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Builder
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonDeserialize(builder = RaceStatus.RaceStatusBuilder.class)
public final class RaceStatus {
  private final Event event;
  private final LocalDateTime startTime;
  private final LocalDateTime splitTime;
  private final LocalDateTime finishTime;
  private final LocalDateTime currentTime;
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