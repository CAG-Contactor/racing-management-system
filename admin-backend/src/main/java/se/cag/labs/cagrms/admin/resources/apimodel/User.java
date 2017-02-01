package se.cag.labs.cagrms.admin.resources.apimodel;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Data;

@Builder(toBuilder = true)
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonDeserialize(builder = User.UserBuilder.class)
public class User {

  private final String id;
  private final String userId;
  private final String displayName;
  @JsonIgnore
  private final String password;

  @JsonPOJOBuilder(withPrefix = "")
  public static final class UserBuilder {
  }
}
