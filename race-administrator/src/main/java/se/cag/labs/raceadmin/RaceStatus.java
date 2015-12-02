package se.cag.labs.raceadmin;

import org.springframework.data.annotation.Id;
import se.cag.labs.usermanager.User;

public class RaceStatus {
    public enum RaceState {
        ACTIVE, INACTIVE
    }

    public enum RaceEvent {
        NONE,START,MIDDLE,FINISH,TIME_OUT_NOT_STARTED, TIME_OUT_NOT_FINISHED,DISQUALIFIED
    }

    @Id
    private String id;
    private User user;
    private RaceEvent event;
    private RaceState state;
    private Long startTime;
    private Long middleTime;
    private Long finishTime;

    public RaceStatus() {
    }

    public RaceStatus(User user, RaceState state) {
        this.user = user;
        this.state = state;
    }

    public RaceStatus(User user, RaceEvent event, RaceState state, Long startTime, Long middleTime, Long finishTime) {
        this.user = user;
        this.event = event;
        this.state = state;
        this.startTime = startTime;
        this.middleTime = middleTime;
        this.finishTime = finishTime;
    }

    public String getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public RaceEvent getEvent() {
        return event;
    }

    public RaceState getState() {
        return state;
    }

    public Long getStartTime() {
        return startTime;
    }

    public Long getMiddleTime() {
        return middleTime;
    }

    public Long getFinishTime() {
        return finishTime;
    }
}
