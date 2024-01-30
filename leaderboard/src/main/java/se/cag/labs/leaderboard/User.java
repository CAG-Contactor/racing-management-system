package se.cag.labs.leaderboard;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class User{

  @Id
  @JsonIgnore
  private String id;
  private Long timestamp;
  private String userId;
  private String displayName;
  private String organisation;
}
