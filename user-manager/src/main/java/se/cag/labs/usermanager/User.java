package se.cag.labs.usermanager;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

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
