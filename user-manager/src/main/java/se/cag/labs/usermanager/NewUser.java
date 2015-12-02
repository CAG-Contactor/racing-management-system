package se.cag.labs.usermanager;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by dawi on 2015-11-20.
 */
@Data
@NoArgsConstructor
public class NewUser {
    private String username;
    private String password;
}
