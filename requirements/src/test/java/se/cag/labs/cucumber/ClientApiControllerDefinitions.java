package se.cag.labs.cucumber;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import se.cag.labs.cagrms.clientapi.controller.ClientApiController;
import se.cag.labs.cagrms.clientapi.service.RaceStatus;


/**
 * Defines all the gherkin-steps that can be used in the current race controller
 */
public class ClientApiControllerDefinitions {

  private static final String CLIENT_API_BASE_URL = "http://localhost:10580";

  private ResponseEntity<RaceStatus> currentStatusEntity;

  @Given("^no active races$")
  public void given_no_active_race() {
    try {
      RestTemplate restTemplate = new RestTemplate();
      ResponseEntity responseEntity = restTemplate.postForEntity(CLIENT_API_BASE_URL + ClientApiController.RESET_RACE, null, ResponseEntity.class);
      Assert.assertTrue("A race wasn't started: " + responseEntity.getStatusCode(), responseEntity.getStatusCode().is2xxSuccessful());

      // TODO abort current race if there is one
    } catch (Exception e) {
      // Never mind this...
    }
  }

  @Given("^a started race$")
  public void given_a_started_race() {
    given_no_active_race();

    RestTemplate restTemplate = new RestTemplate();
    ResponseEntity responseEntity = restTemplate.postForEntity(CLIENT_API_BASE_URL + ClientApiController.REGISTER_FOR_RACE_URL + "?callbackUrl=" + getClientApiBaseURL(), null, ResponseEntity.class);
    Assert.assertTrue("A race wasn't started: " + responseEntity.getStatusCode(), responseEntity.getStatusCode().is2xxSuccessful());
  }

  @When("^the client queries the current status$")
  public void when_query_current_status() throws Throwable {
    RestTemplate restTemplate = new RestTemplate();
    currentStatusEntity = restTemplate.getForEntity(CLIENT_API_BASE_URL + ClientApiController.CURRENT_RACE_URL, RaceStatus.class);
  }

  @Then("^the current status should be empty$")
  public void then_empty_status_returned() throws Throwable {
    Assert.assertEquals(HttpStatus.OK, currentStatusEntity.getStatusCode());

    RaceStatus status = currentStatusEntity.getBody();
    Assert.assertNotNull("A status should have been returned", status);
    Assert.assertNull("No event should be returned", status.getEvent());
    Assert.assertNull("No time should be returned", status.getCurrentTime());
    Assert.assertEquals("The state should be inactive", RaceStatus.State.INACTIVE, status.getState());
  }

  @Then("^the current status should have state \"(.*)\"$")
  public void assert_curret_state_is(RaceStatus.State state) throws Throwable {
    Assert.assertEquals("The state should be inactive", state, currentStatusEntity.getBody().getState());
  }
}