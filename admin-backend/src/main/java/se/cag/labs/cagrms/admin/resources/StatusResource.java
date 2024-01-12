package se.cag.labs.cagrms.admin.resources;


import lombok.extern.log4j.Log4j;
import se.cag.labs.cagrms.admin.AdminConfiguration;
import se.cag.labs.cagrms.admin.resources.apimodel.Service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;

@Path("/admin/")
@Slf4j
public class StatusResource {

    private Client client;
    private final String urlLeaderboardBaseURI;
    private final String urlCurrentRaceBaseURI;
    private final String urlRaceAdministratorBaseURI;
    private final String urlClientApiBaseURI;
    private final String urlUserManagerBaseURI;

    public StatusResource(AdminConfiguration configuration, Client client) {
        this.client = client;
        this.urlLeaderboardBaseURI = configuration.getUrlLeaderboardBaseURI();
        this.urlCurrentRaceBaseURI = configuration.getUrlCurrentRaceBaseURI();
        this.urlRaceAdministratorBaseURI = configuration.getUrlRaceAdministratorBaseURI();
        this.urlUserManagerBaseURI = configuration.getUrlUserManagerBaseURI();
        this.urlClientApiBaseURI = configuration.getUrlClientAPIBaseURI();
    }

    @GET
    @Path("/status")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Service> getServiceStatus() {

        List services = new ArrayList();
        services.add(Service.builder().name("CurrentRace").alive(isAlive(urlCurrentRaceBaseURI)).build());
        services.add(Service.builder().name("LeaderBoard").alive(isAlive(urlLeaderboardBaseURI)).build());
        services.add(Service.builder().name("UserManager").alive(isAlive(urlUserManagerBaseURI)).build());
        services.add(Service.builder().name("RaceAdministrator").alive(isAlive(urlRaceAdministratorBaseURI)).build());
        services.add(Service.builder().name("ClientAPI").alive(isAlive(urlClientApiBaseURI)).build());

        return services;
    }

    private boolean isAlive(String target) {
        log.info("Ping " + target);
        WebTarget webTarget = client.target(target + "/ping");
        Invocation.Builder invocationBuilder =  webTarget.request();

        try {
            return invocationBuilder.get().getStatus() / 100 == 2;
        } catch(Throwable e) {
            return false;
        }
    }
}

