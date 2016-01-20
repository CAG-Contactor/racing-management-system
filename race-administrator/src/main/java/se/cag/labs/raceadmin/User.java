package se.cag.labs.raceadmin;

import com.fasterxml.jackson.annotation.*;
import lombok.*;
import org.springframework.data.annotation.*;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class User {

  @Id
  @JsonIgnore
  private String id;
  private Long timestamp;
  private String userId;
  private String displayName;
}
