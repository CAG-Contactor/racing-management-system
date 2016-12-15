package se.cag.labs.cagrms.admin;

import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

public class AdminApplicationTest {
  @Rule
  public MockitoRule rule = MockitoJUnit.rule();
  @InjectMocks
  private AdminApplication adminApplication;

  @Ignore
  @Test
  public void it_should_run() throws Exception {
    adminApplication.run(null, null);
  }
}