package se.cag.labs.leaderboard;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class LeaderBoardController {
    @Autowired
    private LeaderBoardRepository repository;

    @RequestMapping(value = "/results", method = RequestMethod.POST)
    public void newResult(@RequestBody UserResult userResult) {
        repository.insert(userResult);
    }

    @RequestMapping(value="/results", method = RequestMethod.GET)
    public UserResult[] results() {
        List<UserResult> all = repository.findAll();
        return all.toArray(new UserResult[all.size()]);
    }
}
