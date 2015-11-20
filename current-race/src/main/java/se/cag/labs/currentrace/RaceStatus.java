package se.cag.labs.currentrace;

import org.springframework.data.annotation.Id;

import java.util.Date;

public class RaceStatus {
    public static final String ID = "ID";
    @Id
    private String id;
    private String raceId = ID;
    private Event event;
    private Date startTime;
    private Date middleTime;
    private Date finishTime;
    private State state;

    public RaceStatus() {}

    public RaceStatus(Event event, Date startTime, Date middleTime, Date finishTime, State state) {
        this.event = event;
        this.startTime = startTime;
        this.middleTime = middleTime;
        this.finishTime = finishTime;
        this.state = state;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getMiddleTime() {
        return middleTime;
    }

    public void setMiddleTime(Date middleTime) {
        this.middleTime = middleTime;
    }

    public Date getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public enum Event {
        NONE,START,MIDDLE,FINISH,TIME_OUT_NOT_STARTED, TIME_OUT_NOT_FINISHED,DISQUALIFIED
    }

    public enum State {
        ACTIVE, INACTIVE
    }

    @Override
    public String toString() {
        return "RaceStatus{" +
                "id='" + id + '\'' +
                ", raceId='" + raceId + '\'' +
                ", event=" + event +
                ", startTime=" + startTime +
                ", middleTime=" + middleTime +
                ", finishTime=" + finishTime +
                ", state=" + state +
                '}';
    }
}
