package se.cag.labs.usermanager;

import com.sun.javafx.beans.IDProperty;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;


/**
 * Created by dawi on 2015-11-20.
 */
public class Session {
    @Id
    private String id;
    private String token;
    private String userId;
    private LocalDateTime timeout;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public LocalDateTime getTimeout() {
        return timeout;
    }

    public void setTimeout(LocalDateTime timeout) {
        this.timeout = timeout;
    }
}
