package se.cag.labs.currentrace;

import com.jayway.restassured.RestAssured;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static com.jayway.restassured.RestAssured.given;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = CurrentRaceApplication.class)
@WebAppConfiguration
@IntegrationTest("server.port:0")
@TestPropertySource(locations="classpath:application-test.properties")
public class CurrentRaceControllerTest {
    @Autowired
    private CurrentRaceRepository repository;
    @Value("${local.server.port}")
    private int port;

    @Before
    public void setup() {
        repository.deleteAll();

        RestAssured.port = port;
    }

    @After
    public void tearDown() {
        repository.deleteAll();
    }

    @Test
    public void canStartRace_POST() {
        given().
                param("callbackUrl", "asd").
        when().
                post("/startRace").
        then().
                statusCode(202);

        assertTrue(repository.findByRaceId(RaceStatus.ID) != null);
    }

    @Test
    public void canNotStartRace_WithGet() {
        given().
                param("callbackUrl", "asd").
        when().
                get("/startRace").
        then().
                statusCode(405);

        assertTrue(repository.findByRaceId(RaceStatus.ID) == null);
    }
}
