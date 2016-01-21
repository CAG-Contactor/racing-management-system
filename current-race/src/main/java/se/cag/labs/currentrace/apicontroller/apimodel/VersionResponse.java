package se.cag.labs.currentrace.apicontroller.apimodel;

import com.fasterxml.jackson.annotation.*;
import io.swagger.annotations.*;
import lombok.*;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class VersionResponse {
    @ApiModelProperty(value = "Application version", required = true)
    @NonNull
    private String version;
}