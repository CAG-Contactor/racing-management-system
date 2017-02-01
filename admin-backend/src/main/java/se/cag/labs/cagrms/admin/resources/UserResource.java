package se.cag.labs.cagrms.admin.resources;


import lombok.extern.log4j.Log4j;
import se.cag.labs.cagrms.admin.AdminConfiguration;
import se.cag.labs.cagrms.admin.resources.apimodel.User;
import se.cag.labs.cagrms.admin.resources.mapper.ModelMapper;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Log4j
@Path("/admin/")
public class UserResource {

    private static final String USERS = "/users";
    private static final String TEXT_CSV = "text/csv";
    private final Client client;
    private final String userManagerBaseURI;

    public UserResource(AdminConfiguration configuration, Client client) {
        this.client = client;
        this.userManagerBaseURI = configuration.getUrlUserManagerBaseURI();
    }

    @GET
    @Path(USERS)
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public List<User> getRegisteredUsers() {
        log.info("/users: application/json");
        List<User> users = getUsers();

        return ModelMapper.createUserResponse(users);
    }

    @GET
    @Path(USERS)
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces({TEXT_CSV})
    public List<User> getRegisteredUsersCSV() {
        log.info("/users: text/csv");

        List<User> users = getUsers();

        return ModelMapper.createUserResponse(users);
    }


    /**
     * Calls user manager REST service in order to get the registered users.
     *
     * @return List of <code>User</code>
     */
    private List<User> getUsers() {
        WebTarget webTarget = client.target(userManagerBaseURI+"/registered-users/");

        Invocation.Builder invocationBuilder =  webTarget.request(MediaType.APPLICATION_JSON);
        Response response = invocationBuilder.get();

        return response.readEntity(new GenericType<List<User>>() {});
    }
}

