package se.cag.labs.currentrace.services;

public enum RegisterSensorFactory {

    INSTANCE;

    public RegisterSensor createRegisterSensorObject(PassageDetectedService.SensorType type) {
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
