package se.cag.labs.cagrms.admin.resources;

import io.dropwizard.testing.junit.ResourceTestRule;
import org.junit.ClassRule;
import org.junit.Ignore;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;


public class RaceResourceTest {

    @ClassRule
    public static final ResourceTestRule resources = ResourceTestRule.builder().build();

    @Ignore
    @Test
    public void getRegisteredRaces() throws Exception {
        assertThat(resources.client().target("/admin/registered-races/").request().get()).isNull();

    }

}