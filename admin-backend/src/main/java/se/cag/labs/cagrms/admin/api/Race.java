package se.cag.labs.cagrms.admin.api;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Data;
import se.cag.labs.leaderboard.ResultType;

@Builder(toBuilder = true)
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonDeserialize(builder = Race.RaceBuilder.class)
public final class Race {

    private final String id;
    private final String userId;
    private final long time;
    private final long splitTime;
    private final ResultType resultType;

    @JsonPOJOBuilder(withPrefix = "")
    public static final class RaceBuilder {
    }
}
