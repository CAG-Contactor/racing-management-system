package se.cag.labs.cagrms.admin.resources;


import se.cag.labs.cagrms.admin.api.apimodel.Service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.client.Client;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;

@Path("/admin/")
public class StatusResource {

    private Client client;

    public StatusResource(Client client) {
        this.client = client;
    }

    @GET
    @Path("/service-status/")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Service> getServiceStatus() {

        // Mock objects
        Service currentRace = new Service("Current-Race", "true", "true", new String[] { "This is a info comment!", "A nother info comment!", "Three" });
        Service leaderBoard = new Service("Leaderboard", "true", "false", new String[] { "This is a info comment!", "A nother info comment!", "Three" });
        List<Service> services = new ArrayList();
        services.add(currentRace);
        services.add(leaderBoard);

        return services;
    }
}

