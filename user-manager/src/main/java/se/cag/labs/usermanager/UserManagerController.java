package se.cag.labs.usermanager;

import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.time.*;
import java.util.*;

@RestController
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class UserManagerController {
  public static final String X_AUTH_TOKEN = "X-AuthToken";

  private static final int SESSION_TIME_MINUTES = 60*24*2;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private SessionRepository sessionRepository;

  @RequestMapping("/users")
  public List<User> getUsers() {
    List<User> result = userRepository.findAll();
    result.stream().forEach(u -> u.setPassword(""));
    return result;
  }

  @RequestMapping(path = "/login", method = RequestMethod.POST)
  public ResponseEntity<UserInfo> login(@RequestBody NewUser user) {
    User u = userRepository.findByUserIdAndPassword(user.getUserId(), user.getPassword());
    if (u == null) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
    }
    Session s = sessionRepository.findByUserId(u.getId());
    if (s == null) {
      // Ny Session
      s = new Session();
      s.setToken(UUID.randomUUID().toString());
      s.setTimeout(LocalDateTime.now().plusMinutes(SESSION_TIME_MINUTES));
      s.setUserId(u.getId());
      sessionRepository.save(s);
    } else {
      if (LocalDateTime.now().isAfter(s.getTimeout())) {
        s.setTimeout(LocalDateTime.now().plusMinutes(SESSION_TIME_MINUTES));
        s.setToken(UUID.randomUUID().toString());
        sessionRepository.save(s);
      } else {
        s.setTimeout(LocalDateTime.now().plusMinutes(SESSION_TIME_MINUTES));
        sessionRepository.save(s);
      }
    }
    return ResponseEntity.ok()
      .header("Access-Control-Expose-Headers", X_AUTH_TOKEN)
      .header(X_AUTH_TOKEN, s.getToken())
      .body(new UserInfo(u.getUserId(), u.getDisplayName()));
  }

  @RequestMapping(path = "/logout", method = RequestMethod.POST)
  public ResponseEntity<Void> logout(@RequestParam Token token) {
    Session s = sessionRepository.findByToken(token.getToken());
    if (s != null) {
      sessionRepository.delete(s);
    }
    return ResponseEntity.ok().build();
  }


  @RequestMapping(path = "/users", method = RequestMethod.GET)
  public ResponseEntity<User> getUserForToken(@RequestParam Token token) {
    Session s = sessionRepository.findByToken(token.getToken());
    if (s == null) {
      return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
    if (LocalDateTime.now().isAfter(s.getTimeout())) {
      return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
    User u = userRepository.findOne(s.getUserId());
    if (u == null) {
      return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
    u.setPassword("");
    return new ResponseEntity<>(u, HttpStatus.OK);
  }

  @RequestMapping(path = "/users", method = RequestMethod.POST)
  @ResponseStatus(HttpStatus.CREATED)
  public ResponseEntity<Void> registerNewUser(@RequestBody NewUser user) {
    User existing = userRepository.findByUserId(user.getUserId());
    if (existing != null) {
      return ResponseEntity.badRequest().body(null);
    }
    if (user.getPassword() == null || user.getPassword().length() < 4) {
      return ResponseEntity.badRequest().body(null);
    }
    User u = new User(user.getUserId(), user.getDisplayName(), user.getPassword());
    userRepository.save(u);
    return ResponseEntity.status(HttpStatus.CREATED).body(null);
  }
}
