/*
 * User: joel
 * Date: 2015-12-15
 * Time: 20:46
 */
package se.cag.labs.cagrms.clientapi.controller;

import io.swagger.annotations.*;
import lombok.extern.log4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import se.cag.labs.cagrms.clientapi.service.*;

@Api(basePath = "*",
        value = "Client API",
        description = "The back-end service facade for the web client of the " +
                "CAG Racing Management System.<br>" +
                "It provides operations that are passed on to the other back-end services.<br>" +
                "Moreover, this service is responible for authenticating requests before passing " +
                "them on."
)
@RestController
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST})
@Log4j
public class ClientApiController {
    @Autowired
    private ForwardingService forwardingService;

    @RequestMapping(value = "/users", method = RequestMethod.POST)
    @ApiOperation(value = "Registers a new user",
            notes = "Registers a new user by forwarding this request to the user-manager")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "The user was created successfully"),
            @ApiResponse(code = 400, message = "The user was not accepeted due to that the user-ID was already in use"),
            @ApiResponse(code = 500, message = "Something went wrong when processing the request")
    })
    public ResponseEntity<Void> registerUser(
            @ApiParam(value = "The new user", required = true)
            @RequestBody User user) {
        log.debug("Add user: " + user);
        return forwardingService.registerUser(user);
    }
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ApiOperation(value = "Login",
            notes = "Login the specified user using the specified credentials")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "The user was logged in successfully"),
            @ApiResponse(code = 401, message = "The user was logged in"),
            @ApiResponse(code = 500, message = "Something went wrong when processing the request")
    })
    public ResponseEntity<User> login(
            @ApiParam(value = "The new user", required = true)
            @RequestBody User user) {
        log.debug("Login user: " + user);
        return forwardingService.login(user);
    }
    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    @ApiOperation(value = "Logout",
            notes = "Logout the user associated with the token specified in the X-AuthToken header.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "The user was logged out successfully"),
            @ApiResponse(code = 500, message = "Something went wrong when processing the request")
    })
    public ResponseEntity<Void> logout(
            @RequestHeader String token) {
        log.debug("Logout: " + token);
        return forwardingService.logout(token);
    }
}
