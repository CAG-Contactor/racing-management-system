package se.cag.labs.usermanager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.*;

@RestController
public class UserManagerController {
    @Autowired
    private UserRepository repository;

    @RequestMapping("/users")
    public List<User> getUsers() {
        return repository.findAll();
    }
}
