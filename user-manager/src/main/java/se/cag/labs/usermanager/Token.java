package se.cag.labs.usermanager;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by dawi on 2015-11-20.
 */
@Data
@NoArgsConstructor
public class Token {
  private String token;

  public Token(String token) {
    this.token = token;
  }
}
