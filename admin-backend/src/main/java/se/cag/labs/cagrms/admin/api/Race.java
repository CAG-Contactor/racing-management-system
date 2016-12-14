package se.cag.labs.cagrms.admin.api;


import se.cag.labs.leaderboard.ResultType;

public class Race {

    private String id;
    private String email;
    private int time;
    private ResultType resultType;

    public Race(String id, String email, int time, ResultType resultType) {
        this.id = id;
        this.email = email;
        this.time = time;
        this.resultType = resultType;
    }

    public String getId(){
        return this.id;
    }

    public String getEmail(){
        return this.email;
    }

    public int getTime(){
        return this.getTime();
    }

    public ResultType getResultType(){
        return this.resultType;
    }
}
