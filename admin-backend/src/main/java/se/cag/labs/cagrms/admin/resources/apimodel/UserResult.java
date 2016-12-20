package se.cag.labs.cagrms.admin.resources.apimodel;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.*;
import org.springframework.data.annotation.Id;
import se.cag.labs.leaderboard.ResultType;
import se.cag.labs.leaderboard.User;

@Builder(toBuilder = true)
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonDeserialize(builder = UserResult.UserResultBuilder.class)
public final class UserResult {
  @Id
  private final String id;
  private final long created;
  private final User user;
  private final long time;
  private final long splitTime;
  private final ResultType result;

  @JsonPOJOBuilder(withPrefix = "")
  public static final class UserResultBuilder {
  }
}
