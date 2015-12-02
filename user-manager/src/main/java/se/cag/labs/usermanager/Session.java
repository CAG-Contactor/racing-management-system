package se.cag.labs.usermanager;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;


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
