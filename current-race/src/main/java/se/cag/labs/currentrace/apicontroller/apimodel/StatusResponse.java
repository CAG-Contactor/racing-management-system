package se.cag.labs.currentrace.apicontroller.apimodel;


import se.cag.labs.currentrace.services.repository.datamodel.RaceStatus;

import java.util.Date;

public class StatusResponse {
    private RaceStatus.Event event;
    private Date startTime;
    private Date middleTime;
    private Date finishTime;
    private RaceStatus.State state;

    public StatusResponse(RaceStatus.Event event, Date startTime, Date middleTime, Date finishTime, RaceStatus.State state) {
        this.event = event;
        this.startTime = startTime;
        this.middleTime = middleTime;
        this.finishTime = finishTime;
        this.state = state;
    }

    public RaceStatus.Event getEvent() {
        return event;
    }

    public Date getStartTime() {
        return startTime;
    }

    public Date getMiddleTime() {
        return middleTime;
    }

    public Date getFinishTime() {
        return finishTime;
    }

    public RaceStatus.State getState() {
        return state;
    }
}
