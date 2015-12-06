package se.cag.labs.currentrace.apicontroller;

import com.jayway.restassured.RestAssured;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import se.cag.labs.currentrace.CurrentRaceApplication;
import se.cag.labs.currentrace.apicontroller.apimodel.RaceStatus;
import se.cag.labs.currentrace.apicontroller.apimodel.User;
import se.cag.labs.currentrace.services.UserManagerService;
import se.cag.labs.currentrace.services.repository.CurrentRaceRepository;
import se.cag.labs.currentrace.services.repository.datamodel.CurrentRaceStatus;

import java.util.List;

import static com.jayway.restassured.RestAssured.given;
import static com.jayway.restassured.RestAssured.when;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = CurrentRaceApplication.class)
@WebAppConfiguration
@IntegrationTest("server.port:0")
@TestPropertySource(locations = "classpath:application-test.properties")
public class CurrentRaceControllerTest {
    @Autowired
    private CurrentRaceRepository repository;
    @Value("${local.server.port}")
    private int port;

    private String callbackUrl;

    @Before
    public void setup() {
        repository.deleteAll();
        callbackUrl = "http://localhost:" + port + "/onracestatusupdate";
        RestAssured.port = port;
    }

    @Test
    public void canStartRace_OnlyByPost() {
        given().param(CurrentRaceController.START_RACE_URL, "asd").when().get("/startRace").then().statusCode(HttpStatus.METHOD_NOT_ALLOWED.value());
        given().param(CurrentRaceController.START_RACE_URL, "asd").when().delete("/startRace").then().statusCode(HttpStatus.METHOD_NOT_ALLOWED.value());
        given().param(CurrentRaceController.START_RACE_URL, "asd").when().put("/startRace").then().statusCode(HttpStatus.METHOD_NOT_ALLOWED.value());
        given().param(CurrentRaceController.START_RACE_URL, "asd").when().patch("/startRace").then().statusCode(HttpStatus.METHOD_NOT_ALLOWED.value());

        given().param("callbackUrl", "asd").
                when().post(CurrentRaceController.START_RACE_URL).
                then().statusCode(HttpStatus.ACCEPTED.value());

        CurrentRaceStatus currentRaceStatus = repository.findByRaceId(CurrentRaceStatus.ID);

        assertNotNull(currentRaceStatus);
        assertEquals(RaceStatus.State.ACTIVE, currentRaceStatus.getState());
    }

    @Test
    public void startRace_returnFoundIfAlreadyActive() {
        CurrentRaceStatus currentRaceStatus = new CurrentRaceStatus();
        currentRaceStatus.setState(RaceStatus.State.ACTIVE);
        repository.save(currentRaceStatus);

        given().param("callbackUrl", "asd").
                when().post(CurrentRaceController.START_RACE_URL).
                then().statusCode(HttpStatus.FOUND.value());
    }

    @Test
    public void canCancelRaceIfStartedAndOnlyPost() {
        when().get(CurrentRaceController.CANCEL_RACE_URL).then().statusCode(HttpStatus.METHOD_NOT_ALLOWED.value());
        when().put(CurrentRaceController.CANCEL_RACE_URL).then().statusCode(HttpStatus.METHOD_NOT_ALLOWED.value());
        when().delete(CurrentRaceController.CANCEL_RACE_URL).then().statusCode(HttpStatus.METHOD_NOT_ALLOWED.value());
        when().patch(CurrentRaceController.CANCEL_RACE_URL).then().statusCode(HttpStatus.METHOD_NOT_ALLOWED.value());

        CurrentRaceStatus currentRaceStatus = new CurrentRaceStatus();
        currentRaceStatus.setState(RaceStatus.State.ACTIVE);
        currentRaceStatus.setCallbackUrl(callbackUrl);
        repository.save(currentRaceStatus);

        when().post(CurrentRaceController.CANCEL_RACE_URL).
                then().statusCode(HttpStatus.ACCEPTED.value());

        currentRaceStatus = repository.findByRaceId(CurrentRaceStatus.ID);

        assertNotNull(currentRaceStatus);
        assertEquals(RaceStatus.State.INACTIVE, currentRaceStatus.getState());
    }

    @Test
    public void canNotCancelRaceIfNotStarted() {
        when().post(CurrentRaceController.CANCEL_RACE_URL).
                then().statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    public void canGetStatusOnlyByGet() {
        when().post(CurrentRaceController.STATUS_URL).then().statusCode(HttpStatus.METHOD_NOT_ALLOWED.value());
        when().delete(CurrentRaceController.STATUS_URL).then().statusCode(HttpStatus.METHOD_NOT_ALLOWED.value());
        when().put(CurrentRaceController.STATUS_URL).then().statusCode(HttpStatus.METHOD_NOT_ALLOWED.value());
        when().patch(CurrentRaceController.STATUS_URL).then().statusCode(HttpStatus.METHOD_NOT_ALLOWED.value());

        CurrentRaceStatus currentRaceStatus = new CurrentRaceStatus();
        currentRaceStatus.setState(RaceStatus.State.ACTIVE);
        repository.save(currentRaceStatus);

        when().get(CurrentRaceController.STATUS_URL).
                then().statusCode(HttpStatus.OK.value()).
                body("state", is(RaceStatus.State.ACTIVE.name()));
    }

    @Test
    public void canUpdatePassageTime_OnlyByPost() {
        given().param("sensorID", "START").param("timestamp", 1234).
                when().get(CurrentRaceController.PASSAGE_DETECTED_URL).then().statusCode(HttpStatus.METHOD_NOT_ALLOWED.value());
        given().param("sensorID", "START_ID").param("timestamp", 1234).
                when().delete(CurrentRaceController.PASSAGE_DETECTED_URL).then().statusCode(HttpStatus.METHOD_NOT_ALLOWED.value());
        given().param("sensorID", "START").param("timestamp", 1234).
                when().put(CurrentRaceController.PASSAGE_DETECTED_URL).then().statusCode(HttpStatus.METHOD_NOT_ALLOWED.value());
        given().param("sensorID", "START").param("timestamp", 1234).
                when().patch(CurrentRaceController.PASSAGE_DETECTED_URL).then().statusCode(HttpStatus.METHOD_NOT_ALLOWED.value());


        CurrentRaceStatus currentRaceStatus = new CurrentRaceStatus();
        currentRaceStatus.setState(RaceStatus.State.ACTIVE);
        currentRaceStatus.setCallbackUrl(callbackUrl);
        repository.save(currentRaceStatus);

        given().param("sensorID", "START").param("timestamp", 1234).
                when().post(CurrentRaceController.PASSAGE_DETECTED_URL).then().statusCode(HttpStatus.ACCEPTED.value());
        given().param("sensorID", "SPLIT").param("timestamp", 12345).
                when().post(CurrentRaceController.PASSAGE_DETECTED_URL).then().statusCode(HttpStatus.ACCEPTED.value());
        given().param("sensorID", "FINISH").param("timestamp", 123456).
                when().post(CurrentRaceController.PASSAGE_DETECTED_URL).then().statusCode(HttpStatus.ACCEPTED.value());

        currentRaceStatus = repository.findByRaceId(CurrentRaceStatus.ID);
        assertNotNull(currentRaceStatus);
        assertEquals(new Long(1234), currentRaceStatus.getStartTime());
        assertEquals(new Long(12345), currentRaceStatus.getMiddleTime());
        assertEquals(new Long(123456), currentRaceStatus.getFinishTime());
        assertEquals(RaceStatus.Event.FINISH, currentRaceStatus.getEvent());
        assertEquals(RaceStatus.State.INACTIVE, currentRaceStatus.getState());
    }

    @Test
    public void canNotUpdatePassageTime_WithFaultySensorID() {
        CurrentRaceStatus currentRaceStatus = new CurrentRaceStatus();
        currentRaceStatus.setState(RaceStatus.State.ACTIVE);
        repository.save(currentRaceStatus);

        given().param("sensorID", "FAULTY").param("timestamp", 1234).
                when().post(CurrentRaceController.PASSAGE_DETECTED_URL).then().statusCode(HttpStatus.EXPECTATION_FAILED.value());
    }

    @Test
    public void secondPassageOfMiddleSensorIsIgnored() {
        CurrentRaceStatus currentRaceStatus = new CurrentRaceStatus();
        currentRaceStatus.setState(RaceStatus.State.ACTIVE);
        currentRaceStatus.setCallbackUrl(callbackUrl);
        repository.save(currentRaceStatus);

        given().param("sensorID", "SPLIT").param("timestamp", 1234).
                when().post(CurrentRaceController.PASSAGE_DETECTED_URL).then().statusCode(HttpStatus.ACCEPTED.value());
        given().param("sensorID", "SPLIT").param("timestamp", 12345).
                when().post(CurrentRaceController.PASSAGE_DETECTED_URL).then().statusCode(HttpStatus.ALREADY_REPORTED.value());

        currentRaceStatus = repository.findByRaceId(CurrentRaceStatus.ID);
        assertNotNull(currentRaceStatus);
        assertEquals(new Long(1234), currentRaceStatus.getMiddleTime());
    }

    @Test
    public void getUsersFromExternalService() {
        UserManagerService userManagerService = new UserManagerService();


         List<User> userList = userManagerService.getUsers();

        assertNotNull(userList);
        //assertEquals("string", userList.get(0).getName());
    }
}
