package se.cag.labs.raceadmin;

import org.springframework.data.annotation.Id;

public class RaceStatus {
    @Id
    private String id;
    private String userId;
    private String event;
    private String state;
    private Long startTime;
    private Long middleTime;
    private Long finishTime;

    public RaceStatus() {
    }

    public RaceStatus(String userId, String event, String state, Long startTime, Long middleTime, Long finishTime) {
        this.userId = userId;
        this.event = event;
        this.state = state;
        this.startTime = startTime;
        this.middleTime = middleTime;
        this.finishTime = finishTime;
    }

    public String getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public String getEvent() {
        return event;
    }

    public String getState() {
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
