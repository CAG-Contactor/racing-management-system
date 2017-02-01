package se.cag.labs.cagrms.admin;

import io.dropwizard.Application;
import io.dropwizard.client.JerseyClientBuilder;
import io.dropwizard.configuration.EnvironmentVariableSubstitutor;
import io.dropwizard.configuration.SubstitutingSourceProvider;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.eclipse.jetty.servlets.CrossOriginFilter;
import se.cag.labs.cagrms.admin.resources.RaceResource;
import se.cag.labs.cagrms.admin.resources.StatusResource;
import se.cag.labs.cagrms.admin.resources.UserResource;
import se.cag.labs.cagrms.admin.resources.csv.CSVMessageBodyWriter;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import javax.ws.rs.client.Client;
import java.util.EnumSet;

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
    environment.jersey().register(new UserResource(configuration, client));
    environment.jersey().register(new StatusResource(configuration, client));
    environment.jersey().register(new CSVMessageBodyWriter());
    configureCors(environment);
  }

  @Override
  public void initialize(Bootstrap<AdminConfiguration> bootstrap) {
    EnvironmentVariableSubstitutor environmentVariableSubstitutor =
        new EnvironmentVariableSubstitutor(false);
    environmentVariableSubstitutor.setEnableSubstitutionInVariables(true);
    bootstrap.setConfigurationSourceProvider(
        new SubstitutingSourceProvider(
            bootstrap.getConfigurationSourceProvider(),
            environmentVariableSubstitutor
        )
    );
  }

  private void configureCors(Environment environment) {
    final FilterRegistration.Dynamic cors =
        environment.servlets().addFilter("CORS", CrossOriginFilter.class);

    // Configure CORS parameters
    cors.setInitParameter(CrossOriginFilter.ALLOWED_ORIGINS_PARAM, "*");
    cors.setInitParameter(CrossOriginFilter.ALLOWED_HEADERS_PARAM, "X-Requested-With,Content-Type,Accept,Origin,Authorization");
    cors.setInitParameter(CrossOriginFilter.ALLOWED_METHODS_PARAM, "OPTIONS,GET,PUT,POST,DELETE,HEAD");
    cors.setInitParameter(CrossOriginFilter.ALLOW_CREDENTIALS_PARAM, "true");

    // Add URL mapping
    cors.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");

  }
}