package se.cag.labs.cagrms.admin.resources.apimodel;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Data;

@Builder(toBuilder = true)
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonDeserialize(builder = Service.ServiceBuilder.class)
public final class Service {

    private final String name;
    private final String alive;
    private final String dbUp;
    private final String [] info;

    @JsonPOJOBuilder(withPrefix = "")
    public static final class ServiceBuilder {
    }
}
