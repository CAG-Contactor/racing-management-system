package se.cag.labs.currentrace.cucumber;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import se.cag.labs.currentrace.apicontroller.CurrentRaceController;
import se.cag.labs.currentrace.apicontroller.apimodel.RaceStatus;

/**
 * Defines all the gherkin-steps that can be used in the current race controller
 */
public class CurrentRaceControllerDefinitions extends AbstractDefinitions {

  private ResponseEntity<RaceStatus> currentStatusEntity;

  @Given("^no active races$")
  public void given_no_active_race() {
    try {
      RestTemplate restTemplate = new RestTemplate();
      restTemplate.postForEntity(getBaseUrl() + CurrentRaceController.CANCEL_RACE_URL, null, RaceStatus.class);
    } catch (Exception e) {
      // Never mind this...
    }
  }

  @Given("^a started race$")
  public void given_a_started_race() {
    given_no_active_race();

    RestTemplate restTemplate = new RestTemplate();
    ResponseEntity responseEntity = restTemplate.postForEntity(getBaseUrl() + CurrentRaceController.START_RACE_URL + "?callbackUrl=" + getBaseUrl(), null, ResponseEntity.class);
    Assert.assertTrue("A race wasn't started: " + responseEntity.getStatusCode(), responseEntity.getStatusCode().is2xxSuccessful());
  }

  @When("^the client queries the current status$")
  public void when_query_current_status() throws Throwable {
    RestTemplate restTemplate = new RestTemplate();
    currentStatusEntity = restTemplate.getForEntity(getBaseUrl() + CurrentRaceController.STATUS_URL, RaceStatus.class);
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