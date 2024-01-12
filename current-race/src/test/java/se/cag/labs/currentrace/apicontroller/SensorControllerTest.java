package se.cag.labs.currentrace.apicontroller;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import se.cag.labs.currentrace.CurrentRaceApplication;
import se.cag.labs.currentrace.services.repository.SensorRepository;
import se.cag.labs.currentrace.services.repository.datamodel.SensorModel;

import static com.jayway.restassured.RestAssured.given;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {CurrentRaceApplication.class},
  webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
  properties = {"classpath:application-test.properties"})
// NOTE!! order is important
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
@ActiveProfiles("test")
public class SensorControllerTest {
  @Autowired
  private ApplicationContext applicationContext; //Needed by nosqlunit
  @Autowired
  private SensorRepository repository;

  @Value("${local.server.port}")
  private int port;

  @Before
  public void setup() {
    MockitoAnnotations.initMocks(this);
    repository.deleteAll();
    RestAssured.port = port;
  }

  @Test
  public void canRegisterSensorByPost() {
    String bodyAsString = "{\"sensorId\": \"pi1\",\"sensorIpAddress\": \"10.0.0.1\"}";

    given()
      .body(bodyAsString).with().contentType(ContentType.JSON)
      .when()
      .get(SensorController.REGISTER_SENSOR_URL)
      .then()
      .statusCode(HttpStatus.METHOD_NOT_ALLOWED.value());

    given()
      .body(bodyAsString).with().contentType(ContentType.JSON)
      .when()
      .put(SensorController.REGISTER_SENSOR_URL)
      .then()
      .statusCode(HttpStatus.METHOD_NOT_ALLOWED.value());

    given().body(bodyAsString).with().contentType(ContentType.JSON)
      .when()
      .patch(SensorController.REGISTER_SENSOR_URL)
      .then()
      .statusCode(HttpStatus.METHOD_NOT_ALLOWED.value());

    given()
      .body(bodyAsString).with().contentType(ContentType.JSON)
      .when()
      .post(SensorController.REGISTER_SENSOR_URL)
      .then()
      .statusCode(HttpStatus.ACCEPTED.value());

    SensorModel sensorModel = repository.findBySensorId("pi1").stream()
      .findFirst()
      .orElse(null);
    assertNotNull(sensorModel);
    assertEquals("10.0.0.1", sensorModel.getSensorIpAddress());

    given()
      .when().get(SensorController.SHOW_REGISTERED_SENSORS_URL)
      .then()
      .statusCode(HttpStatus.OK.value());
  }

  @Test
  public void canNotRegisterSensorWithIllegalAddress() {
    String bodyAsString = "{\"sensorId\": \"pi1\",\"sensorIpAddress\": \"10.0.a.1\"}";

    given()
      .body(bodyAsString).with().contentType(ContentType.JSON)
      .when()
      .post(SensorController.REGISTER_SENSOR_URL)
      .then()
      .statusCode(HttpStatus.EXPECTATION_FAILED.value());
  }

  @Test
  public void canNotRegisterSensorWithoutAddress() {
    String bodyAsString = "{\"sensorId\": \"pi1\"}";

    given()
      .body(bodyAsString).with().contentType(ContentType.JSON)
      .when()
      .post(SensorController.REGISTER_SENSOR_URL)
      .then()
      .statusCode(HttpStatus.EXPECTATION_FAILED.value());

    assertTrue(repository.findBySensorId("pi1").isEmpty());
  }
}