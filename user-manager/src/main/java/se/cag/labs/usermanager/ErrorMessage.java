package se.cag.labs.usermanager;

import lombok.Data;

/**
 * Created by dawi on 2015-11-20.
 */
@Data
public class ErrorMessage {
  private String message;

  public ErrorMessage(String message) {
    this.message = message;
  }
}
