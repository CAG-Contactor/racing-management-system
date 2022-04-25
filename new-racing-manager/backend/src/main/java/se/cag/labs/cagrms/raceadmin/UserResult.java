package se.cag.labs.cagrms.raceadmin;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class UserResult {
  private long created = System.currentTimeMillis();
  private User user;
  private long time;
  private long splitTime;
  private ResultType result;

  public enum ResultType {
    FINISHED, WALKOVER, DISQUALIFIED
  }
}
