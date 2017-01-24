package se.cag.labs.usermanager;

import com.fasterxml.jackson.annotation.*;
import lombok.*;
import org.springframework.data.annotation.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User extends NewUser {
  @Id
  @JsonIgnore
  private String id;

  public User(final String userId, final String displayName, final String organisation, final String password) {
    super(userId, displayName, organisation, password);
  }
}
