package se.cag.labs.cagrms.admin;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import io.dropwizard.client.JerseyClientConfiguration;

import javax.validation.Valid;

/**
 * Configuration for the admin client.
 */
public class AdminConfiguration extends Configuration {

    private String urlLeaderboardResults;
    private String urlCancelCurrentRace;

    @Valid
    @JsonProperty
    private JerseyClientConfiguration jerseyClient = new JerseyClientConfiguration();

    @JsonProperty("jerseyClient")
    public JerseyClientConfiguration getJerseyClientConfiguration() {
        return jerseyClient;
    }

    @JsonProperty("urlLeaderboardResults")
    public String getUrlLeaderboardResults() {
        return urlLeaderboardResults;
    }

    @JsonProperty("urlCancelCurrentRace")
    public String getUrlCancelCurrentRace() {
        return urlCancelCurrentRace;
    }
}
