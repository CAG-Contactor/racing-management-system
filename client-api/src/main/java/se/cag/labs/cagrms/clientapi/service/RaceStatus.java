package se.cag.labs.cagrms.clientapi.service;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class RaceStatus {
  private String id;
  private String raceId;
  private String callbackUrl;
  private RaceStatus.Event event;
  private Long raceActivatedTime;
  private Long startTime;
  private Long splitTime;
  private Long finishTime;
  private Long currentTime;
  private RaceStatus.State state;
  private User user;
  public enum Event {
    NONE, START, SPLIT, FINISH, TIME_OUT_NOT_STARTED, TIME_OUT_NOT_FINISHED, DISQUALIFIED
  }
  public enum State {
    ACTIVE, INACTIVE
  }
}
