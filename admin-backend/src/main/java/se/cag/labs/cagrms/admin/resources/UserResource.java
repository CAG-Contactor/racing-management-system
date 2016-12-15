package se.cag.labs.cagrms.admin.resources;


import se.cag.labs.cagrms.admin.api.apimodel.User;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.client.Client;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;

@Path("/admin/")
public class UserResource {

    private Client client;

    public UserResource(Client client) {
        this.client = client;
    }

    @GET
    @Path("/users/")
    @Produces(MediaType.APPLICATION_JSON)
    public List<User> getRegisteredUsers() {

        List<User> users = new ArrayList();
        users.add(new User("anders.andersson@gmail.com", "andersson", "3nk)ddd/"));
        users.add(new User("johan.johansson@gmail.com", "johansson", "34g/%&&%ss/"));

        return users;
    }

    @GET
    @Path("/users/")
    @Produces(MediaType.TEXT_PLAIN)
    public List<User> getRegisteredUsersCSV() {


        return new ArrayList<User>();
    }

}

