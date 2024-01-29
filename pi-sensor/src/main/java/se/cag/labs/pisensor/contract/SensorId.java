package se.cag.labs.pisensor.contract;


public enum SensorId {
    START(17, true),
    SPLIT(27, true),
    FINISH(22, true);

    private final boolean shouldTriggerOnHigh;
    private final int bcmPin;

    SensorId(int pin, boolean shouldTriggerOnHigh) {
        this.bcmPin = pin;
        this.shouldTriggerOnHigh = shouldTriggerOnHigh;
    }

    public boolean shouldTriggerOnHigh() {
        return shouldTriggerOnHigh;
    }

    public int getPin() {
        return bcmPin;
    }
}
