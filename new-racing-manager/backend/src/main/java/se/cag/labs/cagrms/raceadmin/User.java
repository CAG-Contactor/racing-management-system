package se.cag.labs.cagrms.raceadmin;

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
