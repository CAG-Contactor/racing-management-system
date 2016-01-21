package se.cag.labs.raceadmin;

import lombok.*;
import se.cag.labs.raceadmin.*;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class UserResult {
    private long created = System.currentTimeMillis();
    private User user;
    private long time;
    private long splitTime;
    private ResultType result;

    public enum ResultType {
        FINISHED, WALKOVER, DISQUALIFIED
    }
}
