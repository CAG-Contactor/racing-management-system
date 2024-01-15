package se.cag.labs.cagrms.admin.resources;

import lombok.extern.slf4j.Slf4j;

import javax.ws.rs.POST;
import javax.ws.rs.Path;

@Slf4j
@Path("/admin/")
public class AuthResource {

    private static final String LOGIN = "/login";

    public AuthResource() {
    }

    @POST
    @Path(LOGIN)
    public void login() {
        // Dummy method provided to add REST endpoint
    }
}

