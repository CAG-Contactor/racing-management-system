package se.cag.labs.currentrace.apicontroller;

import com.jayway.restassured.*;
import org.jmock.*;
import org.jmock.integration.junit4.*;
import org.jmock.lib.concurrent.*;
import org.jmock.lib.legacy.*;
import org.junit.*;
import org.junit.runner.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.*;
import org.springframework.context.annotation.*;
import org.springframework.http.*;
import org.springframework.test.annotation.*;
import org.springframework.test.context.*;
import org.springframework.test.context.junit4.*;
import org.springframework.test.context.web.*;
import org.springframework.test.util.*;
import org.springframework.web.client.*;
import se.cag.labs.currentrace.*;
import se.cag.labs.currentrace.apicontroller.apimodel.*;
import se.cag.labs.currentrace.services.*;
import se.cag.labs.currentrace.services.repository.*;
import se.cag.labs.currentrace.services.repository.datamodel.*;

import java.util.*;

import static com.jayway.restassured.RestAssured.*;
import static org.hamcrest.core.Is.*;
import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {CurrentRaceApplication.class,CurrentRaceControllerTest.Config.class}) // NOTE!! order is important
@WebAppConfiguration
@IntegrationTest("server.port:0")
@TestPropertySource(locations = "classpath:application-test.properties")
@DirtiesContext(classMode= DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class CurrentRaceControllerTest {
    @Rule
    @Autowired
    public JUnitRuleMockery context;
    @Autowired
    private CurrentRaceRepository repository;
    @Autowired
    private CallbackService callbackService; // This is a singleton and is the same instance as injected into application services

    @Value("${local.server.port}")
    private int port;

    private String callbackUrl;
    private RestTemplate restTemplateMock;

    @Before
    public void setup() {
        restTemplateMock = context.mock(RestTemplate.class);
        ReflectionTestUtils.setField(callbackService, "restTemplate", restTemplateMock);
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
        context.checking(new Expectations(){{
            oneOf(restTemplateMock).postForLocation(
                    with(equal("http://localhost:8080/onracestatusupdate")),
                    with(equal(new RaceStatus(
                            null,
                            null,
                            null,
                            null,
                            RaceStatus.State.INACTIVE
                    ))),
                    with(equal(new Object[0]))
            );
        }});

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
        context.checking(new Expectations(){{
            oneOf(restTemplateMock).postForLocation(
                    with(equal("http://localhost:8080/onracestatusupdate")),
                    with(equal(new RaceStatus(
                            RaceStatus.Event.START,
                            new Date(1234),
                            null,
                            null,
                            RaceStatus.State.ACTIVE
                    ))),
                    with(equal(new Object[0]))
            );
            oneOf(restTemplateMock).postForLocation(
                    with(equal("http://localhost:8080/onracestatusupdate")),
                    with(equal(new RaceStatus(
                            RaceStatus.Event.MIDDLE,
                            new Date(1234),
                            new Date(12345),
                            null,
                            RaceStatus.State.ACTIVE
                    ))),
                    with(equal(new Object[0]))
            );
            oneOf(restTemplateMock).postForLocation(
                    with(equal("http://localhost:8080/onracestatusupdate")),
                    with(equal(new RaceStatus(
                            RaceStatus.Event.FINISH,
                            new Date(1234),
                            new Date(12345),
                            new Date(123456),
                            RaceStatus.State.INACTIVE
                    ))),
                    with(equal(new Object[0]))
            );
        }});
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

        context.assertIsSatisfied();
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
        context.checking(new Expectations(){{
            oneOf(restTemplateMock).postForLocation(
                    with(equal("http://localhost:8080/onracestatusupdate")),
                    with(equal(new RaceStatus(
                            RaceStatus.Event.MIDDLE,
                            null,
                            new Date(1234),
                            null,
                            RaceStatus.State.ACTIVE
                    ))),
                    with(equal(new Object[0]))
            );
        }});
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


    public static class Config {
        @Bean
        public JUnitRuleMockery context() {
            JUnitRuleMockery jUnitRuleMockery = new JUnitRuleMockery();
            jUnitRuleMockery.setImposteriser(ClassImposteriser.INSTANCE);
            jUnitRuleMockery. setThreadingPolicy(new Synchroniser());
            return jUnitRuleMockery;
        }
    }
}
