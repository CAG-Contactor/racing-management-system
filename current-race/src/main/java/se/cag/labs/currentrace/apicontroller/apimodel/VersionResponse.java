package se.cag.labs.currentrace.apicontroller.apimodel;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class VersionResponse {
  @ApiModelProperty(value = "Application version", required = true)
  @NonNull
  private String version;
}