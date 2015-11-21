package se.cag.labs.currentrace.apimodel;

import io.swagger.annotations.ApiModelProperty;

public class VersionResponse {
    @ApiModelProperty(value = "Application version", required = true)
    private String version;

    public VersionResponse(String version) {
        this.version = version;
    }

    public String getVersion() {
        return version;
    }
}