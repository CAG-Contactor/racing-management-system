package se.cag.labs.raceadmin;

import com.jayway.restassured.RestAssured;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static com.jayway.restassured.RestAssured.given;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = {"classpath:application-test.properties"})
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
@ActiveProfiles("test")
public class RaceAdministratorControllerTest {

    @Value("${local.server.port}")
    private int port;
    public static final String PING_URL = "/ping";

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        RestAssured.port = port;
    }

    @Test
    public void pingShouldReturnStatusOK() throws Exception {
        given()
                .when()
                .get(PING_URL)
                .then()
                .statusCode(HttpStatus.OK.value());
    }

}