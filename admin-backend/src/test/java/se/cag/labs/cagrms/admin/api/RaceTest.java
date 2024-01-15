package se.cag.labs.cagrms.admin.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import se.cag.labs.leaderboard.ResultType;
import static io.dropwizard.testing.FixtureHelpers.*;
import static org.assertj.core.api.Assertions.assertThat;

public class RaceTest {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    private Race race;

    @Before
    public void setup() {
        race = Race.builder()
                .id("56bb20bae4b043f180920c26")
                .userId("askiajonsson@hotmail.com")
                .time(108892)
                .splitTime(41857)
                .resultType(ResultType.FINISHED)
                .build();
    }

    @Test
    public void serializesToJSON() throws Exception {
        final String expected = MAPPER.writeValueAsString(
                MAPPER.readValue(fixture("fixtures/race.json"), Race.class));

        assertThat(MAPPER.writeValueAsString(race)).isEqualTo(expected);
    }

    @Test
    public void deserializesFromJSON() throws Exception {

        assertThat(MAPPER.readValue(fixture("fixtures/race.json"), Race.class))
                .isEqualTo(race);
    }

}