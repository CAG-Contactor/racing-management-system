package se.cag.labs.cagrms.admin;

import io.dropwizard.Application;
import io.dropwizard.setup.Environment;

/**
 * This is the main application class for the admin backend.
 */
public class AdminApplication extends Application<AdminConfiguration> {
  public static void main(String... args) throws Exception {
    new AdminApplication().run(args);
  }

  @Override
  public void run(AdminConfiguration configuration, Environment environment) throws Exception {

  }
}
