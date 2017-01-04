package se.cag.labs.cagrms.admin;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import io.dropwizard.client.JerseyClientConfiguration;

import javax.validation.Valid;

/**
 * Configuration class for the admin client. Reads properties from admin-conf.yml
 */
public class AdminConfiguration extends Configuration {

    private String urlLeaderboardResults;
    private String urlCancelCurrentRace;
    private String urlRaceAdministrator;

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

    @JsonProperty("urlRaceAdministrator")
    public String getUrlRaceAdministrator() {
        return urlRaceAdministrator;
    }
}
