package se.cag.labs.currentrace.apicontroller.apimodel;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.*;
import lombok.*;

@Builder
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonDeserialize(builder = User.UserBuilder.class)
public final class User {
  private final String id;
  private final String name;
  private final String password;

  @JsonPOJOBuilder(withPrefix = "")
  public static final class UserBuilder {
  }
}
