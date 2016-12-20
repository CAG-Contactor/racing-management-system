package se.cag.labs.cagrms.admin.resources;


import se.cag.labs.cagrms.admin.api.Race;
import se.cag.labs.cagrms.admin.resources.apimodel.UserResult;
import se.cag.labs.cagrms.admin.resources.mapper.ModelMapper;

import javax.ws.rs.*;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Path("/admin/")
public class RaceResource {

    private Client client;

    public RaceResource(Client client) {
        this.client = client;
    }

    @GET
    @Path("/registered-races/")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Race> getRegisteredRaces() {

        WebTarget webTarget = client.target("http://localhost:10180/results");

        Invocation.Builder invocationBuilder =  webTarget.request(MediaType.APPLICATION_JSON);
        Response response = invocationBuilder.get();
        List<UserResult> results = response.readEntity(new GenericType<List<UserResult>>() {});

        return ModelMapper.createUserResultResponse(results);
    }

    @GET
    @Path("/registered-races/")
    @Produces(MediaType.TEXT_PLAIN)
    public List<UserResult> getRegisteredRacesCSV() {

        return new ArrayList();
    }

    @DELETE
    @Path("/registered-races/{id}")
    public void deleteRace() {

    }

    @POST
    @Path("/registered-races/{id}")
    public void cancelRace() {

    }
}
