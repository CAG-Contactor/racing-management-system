package se.cag.labs.usermanager;

import lombok.*;
import org.springframework.data.annotation.*;

import java.time.*;


/**
 * Created by dawi on 2015-11-20.
 */
@Data
public class Session {
  @Id
  private String id;
  private String token;
  private String userId;
  private LocalDateTime timeout;
}
