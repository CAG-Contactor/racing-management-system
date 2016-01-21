package se.cag.labs.usermanager;

import io.swagger.annotations.*;
import lombok.*;

/**
 * Created by dawi on 2015-11-20.
 */
@Data
@NoArgsConstructor
public class Token {
  @ApiModelProperty(value = "Session ID", required = true)
  private String token;

  public Token(String token) {
    this.token = token;
  }
}
