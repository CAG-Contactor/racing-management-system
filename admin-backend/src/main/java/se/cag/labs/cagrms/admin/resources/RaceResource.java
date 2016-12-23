package se.cag.labs.cagrms.admin.resources;


import lombok.extern.log4j.Log4j;
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
import java.util.List;

@Log4j
@Path("/admin")
public class RaceResource {

    public static final String URL_LEADERBOARD_RESULTS = "http://localhost:10180/results";
    private Client client;

    public RaceResource(Client client) {
        this.client = client;
    }

    @GET
    @Path("/registered-races/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public List<Race> getRegisteredRaces() {
        log.debug("/registered-races: application/json");
        List<UserResult> results = getUserResults();

        return ModelMapper.createUserResultResponse(results);
    }


    @GET
    @Path("/registered-races/")
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces({"text/csv"})
    public List<Race> getRegisteredRacesCSV() {
        log.debug("/registered-races: text/csv");
        List<UserResult> results = getUserResults();

        return ModelMapper.createUserResultResponse(results);
    }

    @DELETE
    @Path("/registered-races/{id}")
    public void deleteRace() {

    }

    @POST
    @Path("/registered-races/{id}")
    public void cancelRace() {

    }

    /**
     * Calls leaderboard REST service in order to get the results from the races.
     *
     * @return List of <code>UserResult</code>
     */
    private List<UserResult> getUserResults() {
        WebTarget webTarget = client.target(URL_LEADERBOARD_RESULTS);

        Invocation.Builder invocationBuilder =  webTarget.request(MediaType.APPLICATION_JSON);
        Response response = invocationBuilder.get();
        return response.readEntity(new GenericType<List<UserResult>>() {});
    }
}
