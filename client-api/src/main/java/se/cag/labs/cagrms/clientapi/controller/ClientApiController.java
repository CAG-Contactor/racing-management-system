/*
 * User: joel
 * Date: 2015-12-15
 * Time: 20:46
 */
package se.cag.labs.cagrms.clientapi.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.cag.labs.cagrms.clientapi.service.ForwardingService;
import se.cag.labs.cagrms.clientapi.service.RaceStatus;
import se.cag.labs.cagrms.clientapi.service.User;
import se.cag.labs.cagrms.clientapi.service.UserResult;

import java.util.List;

@RestController
@CrossOrigin(origins = { "http://localhost:3000", "http://192.168.0.50:30080" }, methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE}, allowCredentials = "true")
@Slf4j
public class ClientApiController {

    public static final String LOGIN_URL = "/login";
    public static final String REGISTER_FOR_RACE_URL = "/userqueue";
    public static final String CURRENT_RACE_URL = "/currentrace";
    public static final String LOGOUT_URL = "/logout";
    public static final String RESET_RACE_URL = "/reset-race";
    public static final String REGISTER_USER_URL = "/users";
    public static final String REGISTER_USER_WITH_QR = "/registerqr";

    @Autowired
    private ForwardingService forwardingService;

    @RequestMapping(value = REGISTER_USER_URL, method = RequestMethod.POST)
    public ResponseEntity<Void> registerUser(
            @RequestBody User user) {
        log.debug("Add user: " + user);
        return forwardingService.registerUser(user);
    }

    @RequestMapping(value = REGISTER_USER_URL, method = RequestMethod.GET)
    public ResponseEntity<User> userForToken(
            @RequestHeader(name = "X-AuthToken") String token) {
        ResponseEntity<User> userForToken = forwardingService.getUserForToken(token);
        if (userForToken.getStatusCode() != HttpStatus.OK) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        log.debug("Get user " + userForToken + "");
        return userForToken;
    }

    @RequestMapping(value = LOGIN_URL, method = RequestMethod.POST)
    public ResponseEntity<User> login(
            @RequestBody User user) {
        log.debug("Login user: " + user);
        return forwardingService.login(user);
    }

    @RequestMapping(value = REGISTER_USER_WITH_QR, method = RequestMethod.POST)
    public ResponseEntity<String> registerAndCreateToken(
            @RequestBody User user) {
        log.debug("Register user: " + user);
        return forwardingService.registerAndCreateToken(user);
    }

    @RequestMapping(value = LOGOUT_URL, method = RequestMethod.POST)
    public ResponseEntity<Void> logout(
            @RequestHeader(name = "X-AuthToken") String token) {
        log.debug("Logout: " + token);
        return forwardingService.logout(token);
    }

    @RequestMapping(value = "/leaderboard", method = RequestMethod.GET)
    public ResponseEntity<List<UserResult>> getLeaderBoard() {
        log.debug("Get leaderboard");
        return forwardingService.getResults();
    }

    @RequestMapping(value = "/myraces", method = RequestMethod.POST)
    public ResponseEntity<List<UserResult>> getRacesBy(@RequestBody User user) {
        log.debug("Get races by user" + user);
        return forwardingService.getResults(user);
    }

    @RequestMapping(value = "/userqueue", method = RequestMethod.GET)
    public ResponseEntity<List<User>> getUserQueue() {
        log.debug("Get user queue");
        return forwardingService.getQueue();
    }

    @RequestMapping(value = "/userqueue", method = RequestMethod.POST)
    public ResponseEntity<Void> registerForRace(
            @RequestHeader(name = "X-AuthToken") String token,
            @RequestBody User user) {
        ResponseEntity<User> userForToken = forwardingService.getUserForToken(token);
        if (userForToken.getStatusCode() != HttpStatus.OK) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        log.debug("Register user " + user + " for race");
        return forwardingService.registerForRace(user);
    }

    @RequestMapping(value = "/userqueue", method = RequestMethod.DELETE)
    public ResponseEntity<Void> unregisterFromRace(
            @RequestHeader(name = "X-AuthToken") String token,
            @RequestBody User user) {
        ResponseEntity<User> userForToken = forwardingService.getUserForToken(token);
        if (userForToken.getStatusCode() != HttpStatus.OK) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        log.debug("Register user " + user + " for race");
        return forwardingService.unregisterFromRace(user);
    }

    @RequestMapping(value = CURRENT_RACE_URL, method = RequestMethod.GET)
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
    public ResponseEntity<RaceStatus> getLastRace() {
        log.debug("Get last race");
        return forwardingService.getLastStatus();
    }

    @RequestMapping(path = "/ping", method = RequestMethod.GET)
    public ResponseEntity<Void> ping() {
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
