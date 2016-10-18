package se.cag.labs.cucumber;

import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import se.cag.labs.cagrms.clientapi.controller.ClientApiController;
import se.cag.labs.cagrms.clientapi.service.RaceStatus;
import se.cag.labs.cagrms.clientapi.service.User;

import java.net.URI;

/**
 * Defines all the gherkin-steps that can be used in the current race controller
 */
public class ClientApiControllerDefinitions {
    private static final String PROPERTY_CLIENT_API_BASE_URL = "client.api.base.url";
    private static final String DEFAULT_CLIENT_API_BASE_URL = "http://localhost:10580";
    private static final String USERNAME = "cuke@umber.com";
    private static final String USER_DISPLAY_NAME = "Cuke Umber";
    private static final String PASSWORD = "password";

    private static final String HEADER_AUTH_TOKEN = "X-AuthToken";

    private ResponseEntity<RaceStatus> currentStatusEntity;
    private String currentAuthToken;
    private RestTemplate restTemplate;

    @Before
    public void setUp() {
        restTemplate = new RestTemplate();
    }

    @Given("^a user has logged in$")
    public void a_user_has_logged_in() throws Throwable {
        User user = User.builder().userId(USERNAME).displayName(USER_DISPLAY_NAME).password(PASSWORD).build();

        restTemplate.setErrorHandler(new EmptyResponseErrorHandler()); // Ignore all errors
        ResponseEntity<User> loggedInUser = restTemplate.postForEntity(getBaseUrl() + ClientApiController.LOGIN_URL, user, User.class);

        // Something went south, is the user registered?
        if (!loggedInUser.getStatusCode().is2xxSuccessful()) {
            ResponseEntity<User> registeredUser = restTemplate.postForEntity(getBaseUrl() + ClientApiController.REGISTER_USER_URL, user, User.class);
            Assert.assertTrue(registeredUser.getStatusCode().is2xxSuccessful());

            // Try to login once more...
            loggedInUser = restTemplate.postForEntity(getBaseUrl() + ClientApiController.LOGIN_URL, user, User.class);
            if (!loggedInUser.getStatusCode().is2xxSuccessful()) {
                Assert.fail("Couldn't login as the user " + USERNAME + ", got response code " + loggedInUser.getStatusCodeValue());
            }
        }

        currentAuthToken = loggedInUser.getHeaders().get(HEADER_AUTH_TOKEN).stream().findFirst().orElse(null);
        Assert.assertNotNull("Didn't receive an auth token", currentAuthToken);
    }

    @Given("^no active races$")
    public void given_no_active_race() {
        while (true) {
            ResponseEntity<RaceStatus> raceStatus = restTemplate.getForEntity(getBaseUrl() + ClientApiController.CURRENT_RACE_URL, RaceStatus.class);
            if (raceStatus.getBody().getState() == RaceStatus.State.ACTIVE) {
                new RestTemplate().postForEntity(getBaseUrl() + ClientApiController.RESET_RACE_URL, null, RaceStatus.class);
            } else {
                break;
            }
        }
    }

    @Given("^a started race$")
    public void given_a_started_race() {
        given_no_active_race();
        ResponseEntity responseEntity = restTemplate.postForEntity(getBaseUrl() + ClientApiController.REGISTER_FOR_RACE_URL + "?callbackUrl=" + getBaseUrl(), null, ResponseEntity.class);
        Assert.assertTrue("A race wasn't started: " + responseEntity.getStatusCode(), responseEntity.getStatusCode().is2xxSuccessful());
    }

    private String getBaseUrl() {
        return System.getProperty(PROPERTY_CLIENT_API_BASE_URL, DEFAULT_CLIENT_API_BASE_URL);
    }


    @When("^the user queries about current races$")
    public void the_user_queries_about_current_races() throws Throwable {
        currentStatusEntity = restTemplate.getForEntity(getBaseUrl() + ClientApiController.CURRENT_RACE_URL, RaceStatus.class);
    }

    @Then("^there is no current races$")
    public void there_is_no_current_races() throws Throwable {
        Assert.assertEquals("The state should be inactive", RaceStatus.State.INACTIVE, currentStatusEntity.getBody().getState());
    }

    @When("^the user registers for a race$")
    public void the_user_registers_for_a_race() throws Throwable {
        User user = User.builder().userId(USERNAME).displayName(USER_DISPLAY_NAME).password(PASSWORD).build();
        final URI uri = UriComponentsBuilder
                .fromHttpUrl(getBaseUrl() + ClientApiController.REGISTER_FOR_RACE_URL)
                .build()
                .toUri();

        HttpHeaders headers = new HttpHeaders();
        headers.set(HEADER_AUTH_TOKEN, currentAuthToken);
        ResponseEntity<Void> response = restTemplate.exchange(uri, HttpMethod.POST, new HttpEntity<>(user, headers), Void.class);
        Assert.assertTrue("A race wasn't started: " + response.getStatusCode(), response.getStatusCode().is2xxSuccessful());

        // We need to wait for the race being registered and activated
        Thread.sleep(1000);
    }

    @Then("^there is an active races$")
    public void there_is_an_active_races() throws Throwable {
        Assert.assertEquals("The state should be inactive", RaceStatus.State.ACTIVE, currentStatusEntity.getBody().getState());
    }
}