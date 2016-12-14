package se.cag.labs.cagrms.admin.resources;


import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class RaceResource {

    private Client client;

    public RaceResource(Client client) {
        this.client = client;

    }

    @GET
    @Path("/registered-races/")
    @Produces(MediaType.APPLICATION_JSON)
    public List getRegisteredRaces() {

        WebTarget webTarget = client.target("http://localhost:10180/");
        Invocation.Builder invocationBuilder =  webTarget.request(MediaType.APPLICATION_JSON);
        Response response = invocationBuilder.get();

        return new ArrayList<String>();
    }
}
