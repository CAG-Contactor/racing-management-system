package se.cag.labs.cagrms.admin.resources;


import lombok.extern.log4j.Log4j;
import se.cag.labs.cagrms.admin.AdminConfiguration;
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

    public static final String REGISTERED_RACES = "/registered-races/";
    public static final String REGISTERED_RACES_ID = "/registered-races/{id}";
    public static final String CANCEL_ACTIVE_RACE_ID = "/cancel-active-race/{id}";

    private final AdminConfiguration configuration;
    private final String urlLeaderboardResults;
    private final Client client;

    public RaceResource(AdminConfiguration configuration, Client client) {
        this.configuration = configuration;
        this.urlLeaderboardResults = configuration.getUrlLeaderboardResults();
        this.client = client;
    }

    @GET
    @Path(REGISTERED_RACES)
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public List<Race> getRegisteredRaces() {
        log.debug("/registered-races: application/json");
        List<UserResult> results = getUserResults();

        return ModelMapper.createUserResultResponse(results);
    }

    @GET
    @Path(REGISTERED_RACES)
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces({"text/csv"})
    public List<Race> getRegisteredRacesCSV() {
        log.debug("/registered-races: text/csv");
        List<UserResult> results = getUserResults();

        return ModelMapper.createUserResultResponse(results);
    }

    @DELETE
    @Path(REGISTERED_RACES_ID)
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteRace(@PathParam("id") String id) {
        log.info("Deleting race with id: " + id);
        List<UserResult> results = getUserResults();

       UserResult userResult = results.stream()
                .filter(o -> o.getId().equals(id))
                .findFirst().orElse(null);

       if(userResult == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
       }

        WebTarget webTarget = client.target(urlLeaderboardResults + userResult.getId());
        Invocation.Builder invocationBuilder =  webTarget.request(MediaType.APPLICATION_JSON);
        Response response = invocationBuilder.delete();
        log.info("Status: " + response.getStatus());

        return Response.ok(userResult).build();
    }

    @POST
    @Path(CANCEL_ACTIVE_RACE_ID)
    public void cancelRace() {

    }

    /**
     * Calls leaderboard REST service in order to get the results from the races.
     *
     * @return List of <code>UserResult</code>
     */
    private List<UserResult> getUserResults() {
        WebTarget webTarget = client.target(urlLeaderboardResults);

        Invocation.Builder invocationBuilder =  webTarget.request(MediaType.APPLICATION_JSON);
        Response response = invocationBuilder.get();
        return response.readEntity(new GenericType<List<UserResult>>() {});
    }
}
