package se.cag.labs.cagrms.admin.api.apimodel;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import se.cag.labs.leaderboard.ResultType;
import se.cag.labs.leaderboard.User;

@Data
@RequiredArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserResult {
  @Id
  @Setter(AccessLevel.PRIVATE)
  private String id;
  private long created = System.currentTimeMillis();
  private User user;
  private long time;
  private long splitTime;
  private ResultType result;
}
