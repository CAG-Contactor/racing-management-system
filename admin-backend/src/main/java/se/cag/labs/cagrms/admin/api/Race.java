package se.cag.labs.cagrms.admin.api;


import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import se.cag.labs.leaderboard.ResultType;

@Data
public class Race {

    cd ra    private String id;
    @Setter(AccessLevel.PRIVATE) private String email;
    @Setter(AccessLevel.PRIVATE) private int time;
    @Setter(AccessLevel.PRIVATE) private ResultType resultType;

    public Race(String id, String email, int time, ResultType resultType) {
        this.id = id;
        this.email = email;
        this.time = time;
        this.resultType = resultType;
    }
}
