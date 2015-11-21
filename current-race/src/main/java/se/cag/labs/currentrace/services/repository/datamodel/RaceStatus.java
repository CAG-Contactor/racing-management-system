package se.cag.labs.currentrace.services.repository.datamodel;

import org.springframework.data.annotation.Id;

public class RaceStatus {
    public static final String ID = "ID";
    @Id
    private String id;
    private String raceId = ID;
    private Event event;
    private Long startTime;
    private Long middleTime;
    private Long finishTime;
    private State state;

    public RaceStatus() {
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public Long getMiddleTime() {
        return middleTime;
    }

    public void setMiddleTime(Long middleTime) {
        this.middleTime = middleTime;
    }

    public Long getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(Long finishTime) {
        this.finishTime = finishTime;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public enum Event {
        NONE, START, MIDDLE, FINISH, TIME_OUT_NOT_STARTED, TIME_OUT_NOT_FINISHED, DISQUALIFIED
    }

    public enum State {
        ACTIVE, INACTIVE
    }
}
