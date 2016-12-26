package se.cag.labs.cagrms.admin;

import io.dropwizard.Application;
import io.dropwizard.client.JerseyClientBuilder;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import se.cag.labs.cagrms.admin.resources.RaceResource;
import se.cag.labs.cagrms.admin.resources.StatusResource;
import se.cag.labs.cagrms.admin.resources.UserResource;
import se.cag.labs.cagrms.admin.resources.csv.CSVMessageBodyWriter;

import javax.ws.rs.client.Client;

/**
 * This is the main application class for the admin backend.
 */
public class AdminApplication extends Application<AdminConfiguration> {

  public static void main(final String[] args) throws Exception {
    new AdminApplication().run(args);
  }

  @Override
  public void run(AdminConfiguration configuration, Environment environment) throws Exception {

      final Client client = new JerseyClientBuilder(environment).using(configuration.getJerseyClientConfiguration())
            .build(getName());
      environment.jersey().register(new RaceResource(configuration, client));
      environment.jersey().register(new UserResource(client));
      environment.jersey().register(new StatusResource(client));
      environment.jersey().register(new CSVMessageBodyWriter());
  }

    @Override
    public void initialize(Bootstrap<AdminConfiguration> bootstrap) {
    }
}