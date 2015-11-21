package se.cag.labs.currentrace.services.repository.datamodel;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
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

    public enum Event {
        NONE, START, MIDDLE, FINISH, TIME_OUT_NOT_STARTED, TIME_OUT_NOT_FINISHED, DISQUALIFIED
    }

    public enum State {
        ACTIVE, INACTIVE
    }
}
