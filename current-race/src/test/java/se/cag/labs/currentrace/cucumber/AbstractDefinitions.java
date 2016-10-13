package se.cag.labs.currentrace.cucumber;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationContextLoader;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import se.cag.labs.currentrace.CurrentRaceApplication;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = CurrentRaceApplication.class, loader = SpringApplicationContextLoader.class)
@WebAppConfiguration
@IntegrationTest
public class AbstractDefinitions
{
  @Value("${server.port}")
  private String serverPort;

  public String getBaseUrl() {
    return "http://localhost:" + serverPort;
  }
}