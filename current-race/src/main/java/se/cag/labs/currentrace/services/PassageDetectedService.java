package se.cag.labs.currentrace.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.cag.labs.currentrace.services.repository.CurrentRaceRepository;
import se.cag.labs.currentrace.services.repository.datamodel.RaceStatus;

import java.util.HashMap;
import java.util.Map;


@Service
public class PassageDetectedService {
    @Autowired
    private CurrentRaceRepository repository;

    public enum SensorType {
        START("START_ID"),
        MIDDLE("MIDDLE_ID"),
        FINISH("FINISH_ID");

        private final String id;
        private static final Map<String, SensorType> lookup = new HashMap<>();

        static {
            for (SensorType type : SensorType.values()) {
                lookup.put(type.getId(), type);
            }
        }

        SensorType(String id) {
            this.id = id;
        }

        public String getId() {
            return id;
        }

        public static SensorType get(String id) {
            return lookup.get(id);
        }
    }

    public enum PassageDetectedStatus {
        ACCEPTED,
        IGNORED,
        ERROR
    }

    public PassageDetectedStatus passageDetected(String sensorID, long timestamp) {
        RaceStatus raceStatus = repository.findByRaceId(RaceStatus.ID);

        SensorType sensorType = SensorType.get(sensorID);

        if (sensorType == null) {
            return PassageDetectedStatus.ERROR;
        }

        if (RaceStatus.State.ACTIVE.equals(raceStatus.getState())) {
            RegisterSensor sensor = RegisterSensorFactory.INSTANCE.createRegisterSensorObject(sensorType);
            if (sensor.updateStatus(raceStatus, timestamp)) {
                repository.save(raceStatus);
                return PassageDetectedStatus.ACCEPTED;
            }
        }

        return PassageDetectedStatus.IGNORED;
    }
}
