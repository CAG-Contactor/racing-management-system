/*
 * User: joel
 * Date: 2015-12-22
 * Time: 23:04
 */
package se.cag.labs.usermanager;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInfo {
  private String userId;
  private String displayName;
  private String organisation;
}
