package se.cag.labs.currentrace.apicontroller;

import io.restassured.RestAssured;
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

import static io.restassured.RestAssured.given;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = {"classpath:application-test.properties"})
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
@ActiveProfiles("test")
public class PingControllerTest {

    @Value("${local.server.port}")
    private int port;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        RestAssured.port = port;
    }

    @Test
    public void pingShouldReturnStatusAccepted() throws Exception {
        given()
            .when()
            .get(PingController.PING_URL)
            .then()
            .statusCode(HttpStatus.ACCEPTED.value());
    }
}