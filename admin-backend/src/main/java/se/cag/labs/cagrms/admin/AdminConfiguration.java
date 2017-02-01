package se.cag.labs.cagrms.admin;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import io.dropwizard.client.JerseyClientConfiguration;

import javax.validation.Valid;

/**
 * Configuration class for the admin client. Reads properties from admin-conf.yml
 */
public class AdminConfiguration extends Configuration {

    private String urlLeaderboardBaseURI;
    private String urlUserManagerBaseURI;
    private String urlRaceAdministratorBaseURI;
    private String urlCurrentRaceBaseURI;
    private String urlClientAPIBaseURI;

    @Valid
    @JsonProperty
    private JerseyClientConfiguration jerseyClient = new JerseyClientConfiguration();

    @JsonProperty("jerseyClient")
    public JerseyClientConfiguration getJerseyClientConfiguration() {
        return jerseyClient;
    }

    @JsonProperty("urlLeaderboardBaseURI")
    public String getUrlLeaderboardBaseURI() {
        return urlLeaderboardBaseURI;
    }

    @JsonProperty("urlCurrentRaceBaseURI")
    public String getUrlCurrentRaceBaseURI() {
        return urlCurrentRaceBaseURI;
    }

    @JsonProperty("urlRaceAdministratorBaseURI")
    public String getUrlRaceAdministratorBaseURI() {
        return urlRaceAdministratorBaseURI;
    }

    @JsonProperty("urlUserManagerBaseURI")
    public String getUrlUserManagerBaseURI() {
        return urlUserManagerBaseURI;
    }

    @JsonProperty("urlClientAPIBaseURI")
    public String getUrlClientAPIBaseURI() {
        return urlClientAPIBaseURI;
    }
}
