package se.cag.labs.currentrace.services.sensors;

import se.cag.labs.currentrace.services.sensors.impl.RegisterSensorFinish;
import se.cag.labs.currentrace.services.sensors.impl.RegisterSensorMiddle;
import se.cag.labs.currentrace.services.sensors.impl.RegisterSensorStart;

public enum RegisterSensorFactory {

    INSTANCE;

    public RegisterSensor createRegisterSensorObject(SensorType type) {
        switch (type) {
            case START:
                return new RegisterSensorStart();
            case MIDDLE:
                return new RegisterSensorMiddle();
            case FINISH:
                return new RegisterSensorFinish();
            default:
                throw new IllegalArgumentException("Type not supported");
        }
    }
}
