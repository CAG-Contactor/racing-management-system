package se.cag.labs.pisensor.contract;

import com.pi4j.io.gpio.Pin;
import com.pi4j.io.gpio.RaspiPin;

public enum SensorId {
    START(RaspiPin.GPIO_00, true),
    SPLIT(RaspiPin.GPIO_02, true),
    FINISH(RaspiPin.GPIO_03, true);

    private final boolean shouldTriggerOnHigh;
    private final Pin pin;

    SensorId(Pin pin, boolean shouldTriggerOnHigh) {
        this.pin = pin;
        this.shouldTriggerOnHigh = shouldTriggerOnHigh;
    }

    public boolean shouldTriggerOnHigh() {
        return shouldTriggerOnHigh;
    }

    public Pin getPin() {
        return pin;
    }
}
