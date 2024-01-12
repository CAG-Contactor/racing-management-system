package se.cag.labs.cagrms.clientapi.service;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class User {
  private String userId;
  private String displayName;
  private String password;
}
