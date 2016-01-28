package se.cag.labs.raceadmin;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@Data
@RequiredArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RaceStatus implements LastRaceStatus {
  @Id
  @Setter(AccessLevel.PRIVATE)
  private String id;
  private User user;
  private RaceEvent event;
  private RaceState state = RaceState.INACTIVE;
  private Long startTime;
  private Long splitTime;
  private Long finishTime;
  private Long currentTime;
  public RaceStatus(User user) {
    this.user = user;
  }
  public enum RaceState {
    ACTIVE, INACTIVE, UNVERIFIED
  }

  public enum RaceEvent {
    NONE, START, SPLIT, FINISH, TIME_OUT_NOT_STARTED, TIME_OUT_NOT_FINISHED, DISQUALIFIED
  }
}
