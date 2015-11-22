package se.cag.labs.currentrace.apicontroller.apimodel;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import se.cag.labs.currentrace.services.repository.datamodel.RaceStatus;

import java.util.Date;

@Data
@RequiredArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StatusResponse {
    private final RaceStatus.Event event;
    private final Date startTime;
    private final Date middleTime;
    private final Date finishTime;
    private final RaceStatus.State state;
}
