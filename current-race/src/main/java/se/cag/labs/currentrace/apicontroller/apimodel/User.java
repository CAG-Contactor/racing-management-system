package se.cag.labs.currentrace.apicontroller.apimodel;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonDeserialize(builder = User.UserBuilder.class)
public final class User {
    private final String id;
    private final String name;
    private final String password;

    @JsonPOJOBuilder(withPrefix = "")
    public static final class UserBuilder {
    }
}
