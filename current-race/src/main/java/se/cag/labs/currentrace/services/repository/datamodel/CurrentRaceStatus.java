package se.cag.labs.currentrace.services.repository.datamodel;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import se.cag.labs.currentrace.apicontroller.apimodel.RaceStatus;

@Document
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CurrentRaceStatus {
    public static final String ID = "ID";
    @Id
    private String id;
    private String raceId = ID;
    private String callbackUrl;
    private RaceStatus.Event event;
    private Long raceActivatedTime;
    private Long startTime;
    private Long middleTime;
    private Long finishTime;
    private RaceStatus.State state;
}
