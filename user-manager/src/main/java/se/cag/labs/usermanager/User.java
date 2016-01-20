package se.cag.labs.usermanager;

import com.fasterxml.jackson.annotation.*;
import lombok.*;
import org.springframework.data.annotation.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User extends NewUser {
    @Id
    @JsonIgnore
    private String id;

    public User(final String userId, final String displayName, final String password) {
        super(userId, displayName, password);
    }
}
