package se.cag.labs.cagrms.admin.api.apimodel;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import se.cag.labs.usermanager.NewUser;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User extends NewUser {
  @Id
  @JsonIgnore
  private String id;

  public User(final String userId, final String displayName, final String password) {
    super(userId, displayName, password);
  }
}
