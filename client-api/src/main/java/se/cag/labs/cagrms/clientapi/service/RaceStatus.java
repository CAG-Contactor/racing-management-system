package se.cag.labs.cagrms.clientapi.service;

import lombok.*;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class RaceStatus {
  public enum Event {
    NONE, START, MIDDLE, FINISH, TIME_OUT_NOT_STARTED, TIME_OUT_NOT_FINISHED, DISQUALIFIED
  }
  public enum State {
    ACTIVE, INACTIVE
  }

  private String id;
  private String raceId;
  private String callbackUrl;
  private RaceStatus.Event event;
  private Long raceActivatedTime;
  private Long startTime;
  private Long middleTime;
  private Long finishTime;
  private RaceStatus.State state;
}
