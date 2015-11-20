package se.cag.labs.raceadmin;

import org.springframework.data.annotation.Id;

public class Race {
    @Id
    private String id;
    private String name;
    private String email;
    private String password;

    public Race() {
    }

    public Race(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }
}
