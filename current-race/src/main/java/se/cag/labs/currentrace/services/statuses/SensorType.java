package se.cag.labs.currentrace.services.statuses;

import java.util.HashMap;
import java.util.Map;

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