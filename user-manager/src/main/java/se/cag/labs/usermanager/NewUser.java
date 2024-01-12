package se.cag.labs.usermanager;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class NewUser extends UserInfo {
  private String password;

  public NewUser(final String userId, final String displayName, final String organisation, final String password) {
    super(userId, displayName, organisation);
    this.password = password;
  }
}
