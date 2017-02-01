package se.cag.labs.cagrms.admin.resources;


import se.cag.labs.cagrms.admin.AdminConfiguration;
import se.cag.labs.cagrms.admin.resources.apimodel.Service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Path("/admin/")
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

        Response currentRace = pingCurrentRace();
        Response leaderBoard = pingLeaderBoard();
        Response userManager = pingUserManager();
        Response raceAdministrator = pingRaceAdministrator();
        Response clientApi = pingClientApi();

        List services = new ArrayList();
        services.add(Service.builder().name("CurrentRace").alive(currentRace.getStatus() == 202 ? true:false).build());
        services.add(Service.builder().name("LeaderBoard").alive(leaderBoard.getStatus() == 202 ? true:false).build());
        services.add(Service.builder().name("UserManager").alive(userManager.getStatus() == 202 ? true:false).build());
        services.add(Service.builder().name("RaceAdministrator").alive(raceAdministrator.getStatus() == 202 ? true:false).build());
        services.add(Service.builder().name("ClientAPI").alive(clientApi.getStatus() == 202 ? true:false).build());

        return services;
    }


    private Response pingCurrentRace() {
        WebTarget webTarget = client.target(urlCurrentRaceBaseURI + "/ping");
        Invocation.Builder invocationBuilder =  webTarget.request();

        return invocationBuilder.get();
    }

    private Response pingLeaderBoard() {
        WebTarget webTarget = client.target(urlLeaderboardBaseURI + "/ping");
        Invocation.Builder invocationBuilder =  webTarget.request();

        return invocationBuilder.get();
    }

    private Response pingUserManager() {
        WebTarget webTarget = client.target(urlUserManagerBaseURI + "/ping");
        Invocation.Builder invocationBuilder =  webTarget.request();

        return invocationBuilder.get();
    }

    private Response pingRaceAdministrator() {
        WebTarget webTarget = client.target(urlRaceAdministratorBaseURI + "/ping");
        Invocation.Builder invocationBuilder =  webTarget.request();

        return invocationBuilder.get();
    }

    private Response pingClientApi() {
        WebTarget webTarget = client.target(urlClientApiBaseURI + "/ping");
        Invocation.Builder invocationBuilder =  webTarget.request();

        return invocationBuilder.get();
    }
}

