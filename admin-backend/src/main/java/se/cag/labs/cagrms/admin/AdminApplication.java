package se.cag.labs.cagrms.admin;

import io.dropwizard.Application;
import io.dropwizard.client.JerseyClientBuilder;
import io.dropwizard.setup.Environment;
import se.cag.labs.cagrms.admin.resources.RaceResource;

import javax.ws.rs.client.Client;

/**
 * This is the main application class for the admin backend.
 */
public class AdminApplication extends Application<AdminConfiguration> {

  public static void main(String... args) throws Exception {
    new AdminApplication().run(args);
  }

  @Override
  public void run(AdminConfiguration configuration, Environment environment) throws Exception {

    final Client client = new JerseyClientBuilder(environment).using(configuration.getJerseyClientConfiguration())
            .build(getName());
    environment.jersey().register(new RaceResource(client));
  }
}