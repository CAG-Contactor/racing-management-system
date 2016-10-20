/*
 * User: joel
 * Date: 2015-12-15
 * Time: 20:46
 */
package se.cag.labs.cagrms.clientapi.controller;

import io.swagger.annotations.*;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.cag.labs.cagrms.clientapi.service.ForwardingService;
import se.cag.labs.cagrms.clientapi.service.RaceStatus;
import se.cag.labs.cagrms.clientapi.service.User;
import se.cag.labs.cagrms.clientapi.service.UserResult;

import java.util.List;

@Api(basePath = "*",
        value = "Client API",
        description = "The back-end service facade for the web client of the " +
                "CAG Racing Management System.<br>" +
                "It provides operations that are passed on to the other back-end services.<br>" +
                "Moreover, this service is responible for authenticating requests before passing " +
                "them on."
)
@RestController
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE})
@Log4j
public class ClientApiController {

    public static final String LOGIN_URL = "/login";
    public static final String REGISTER_FOR_RACE_URL = "/userqueue";
    public static final String CURRENT_RACE_URL = "/currentrace";
    public static final String LOGOUT_URL = "/logout";
    public static final String RESET_RACE_URL = "/reset-race";
    public static final String REGISTER_USER_URL = "/users";

    @Autowired
    private ForwardingService forwardingService;

    @RequestMapping(value = REGISTER_USER_URL, method = RequestMethod.POST)
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

    @RequestMapping(value = LOGIN_URL, method = RequestMethod.POST)
    @ApiOperation(value = "Login",
            notes = "Login the specified user using the specified credentials")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "The user was logged in successfully, the auth token is provided through the header x-authtoken"),
            @ApiResponse(code = 401, message = "The user was logged in"),
            @ApiResponse(code = 500, message = "Something went wrong when processing the request")
    })
    public ResponseEntity<User> login(
            @ApiParam(value = "The new user", required = true)
            @RequestBody User user) {
        log.debug("Login user: " + user);
        return forwardingService.login(user);
    }

    @RequestMapping(value = LOGOUT_URL, method = RequestMethod.POST)
    @ApiOperation(value = "Logout",
            notes = "Logout the user associated with the token specified in the X-AuthToken header.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "The user was logged out successfully"),
            @ApiResponse(code = 500, message = "Something went wrong when processing the request")
    })
    public ResponseEntity<Void> logout(
            @RequestHeader(name = "X-AuthToken") String token) {
        log.debug("Logout: " + token);
        return forwardingService.logout(token);
    }

    @RequestMapping(value = "/leaderboard", method = RequestMethod.GET)
    @ApiOperation(value = "The leaderboard",
            notes = "Gets the current leaderboards")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "The request was handled succesfully and a leaderboard is returned in the body."),
            @ApiResponse(code = 500, message = "Something went wrong when processing the request")
    })
    public ResponseEntity<List<UserResult>> getLeaderBoard() {
        log.debug("Get leaderboard");
        return forwardingService.getResults();
    }

    @RequestMapping(value = "/myraces", method = RequestMethod.POST)
    @ApiOperation(value = "My races",
            notes = "Gets my races")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "The request was handled succesfully and a my races is returned in the body."),
            @ApiResponse(code = 500, message = "Something went wrong when processing the request")
    })
    public ResponseEntity<List<UserResult>> getRacesBy(@RequestBody User user) {
        log.debug("Get races by user" + user);
        return forwardingService.getResults(user);
    }

    @RequestMapping(value = "/userqueue", method = RequestMethod.GET)
    @ApiOperation(value = "The user queue",
            notes = "Gets the current queue of registered competitors")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "The request was handled succesfully and a leaderboard is returned in the body."),
            @ApiResponse(code = 500, message = "Something went wrong when processing the request")
    })
    public ResponseEntity<List<User>> getUserQueue() {
        log.debug("Get user queue");
        return forwardingService.getQueue();
    }

    @RequestMapping(value = "/userqueue", method = RequestMethod.POST)
    @ApiOperation(value = "Add to the user queue",
            notes = "Register a competitor")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "The request was handled succesfully and a leaderboard is returned in the body."),
            @ApiResponse(code = 500, message = "Something went wrong when processing the request")
    })
    public ResponseEntity<Void> registerForRace(
            @RequestHeader(name = "X-AuthToken") String token,
            @ApiParam(value = "The user to register", required = true)
            @RequestBody User user) {
        ResponseEntity<User> userForToken = forwardingService.getUserForToken(token);
        if (userForToken.getStatusCode() != HttpStatus.OK) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        log.debug("Register user " + user + " for race");
        return forwardingService.registerForRace(user);
    }

    @RequestMapping(value = "/userqueue", method = RequestMethod.DELETE)
    @ApiOperation(value = "Remove the user from the queue",
            notes = "Unregister a competitor")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "The request was handled succesfully and a leaderboard is returned in the body."),
            @ApiResponse(code = 500, message = "Something went wrong when processing the request")
    })
    public ResponseEntity<Void> unregisterFromRace(
            @RequestHeader(name = "X-AuthToken") String token,
            @ApiParam(value = "The user to unregister", required = true)
            @RequestBody User user) {
        ResponseEntity<User> userForToken = forwardingService.getUserForToken(token);
        if (userForToken.getStatusCode() != HttpStatus.OK) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        log.debug("Register user " + user + " for race");
        return forwardingService.unregisterFromRace(user);
    }

    @RequestMapping(value = CURRENT_RACE_URL, method = RequestMethod.GET)
    @ApiOperation(value = "The current race",
            notes = "Gets information about the current race")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "The request was handled succesfully and a leaderboard is returned in the body."),
            @ApiResponse(code = 500, message = "Something went wrong when processing the request")
    })
    public ResponseEntity<RaceStatus> getCurrentRace() {
        log.debug("Get current race");
        return forwardingService.getStatus();
    }


    @RequestMapping(value = RESET_RACE_URL, method = RequestMethod.POST)
    public ResponseEntity<Void> resetRace() {
        log.debug("Reset current race");
        return forwardingService.resetRace();
    }

    @RequestMapping(value = "/lastrace", method = RequestMethod.GET)
    @ApiOperation(value = "The last race",
            notes = "Gets information about the last race")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "The request was handled succesfully."),
            @ApiResponse(code = 500, message = "Something went wrong when processing the request")
    })
    public ResponseEntity<RaceStatus> getLastRace() {
        log.debug("Get last race");
        return forwardingService.getLastStatus();
    }
}
