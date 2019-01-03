package se.cag.jfokus.braccio.data;

import java.beans.Transient;

public class BraccioCommand {

    /**
     *    Step Delay: a milliseconds delay between the movement of each servo.  Allowed values from 10 to 30 msec.
     *    M1=base degrees. Allowed values from 0 to 180 degrees
     *    M2=shoulder degrees. Allowed values from 15 to 165 degrees
     *    M3=elbow degrees. Allowed values from 0 to 180 degrees
     *    M4=wrist vertical degrees. Allowed values from 0 to 180 degrees
     *    M5=wrist rotation degrees. Allowed values from 0 to 180 degrees
     *    M6=gripper degrees. Allowed values from 10 to 73 degrees. 10: the toungue is open, 73: the gripper is closed.
     */

    public static final int DEFAULT_ANGLE = 90;
    public static final int DEFAULT_GRIPPER = 73;
    public static final BraccioCommand defaultPosition = new Builder().build();

    public BraccioCommand() {
        // JSON constructor.
    }

    public BraccioCommand(int delay, int base, int shoulder, int elbow, int wrist, int wristRotation, int gripper) {
        this.delay = delay;
        this.base = base;
        this.shoulder = shoulder;
        this.elbow = elbow;
        this.wrist = wrist;
        this.wristRotation = wristRotation;
        this.gripper = gripper;
    }

    public BraccioCommand(int base, int shoulder, int elbow, int wrist, int wristRotation, int gripper) {
        this.delay = 20;
        this.base = base;
        this.shoulder = shoulder;
        this.elbow = elbow;
        this.wrist = wrist;
        this.wristRotation = wristRotation;
        this.gripper = gripper;
    }

    private int delay;
    private int base;
    private int shoulder;
    private int elbow;
    private int wrist;
    private int wristRotation;
    private int gripper;

    public int getBase() {
        return base;
    }

    public void setBase(int base) {
        this.base = base;
    }

    public int getShoulder() {
        return shoulder;
    }

    public void setShoulder(int shoulder) {
        this.shoulder = shoulder;
    }

    public int getElbow() {
        return elbow;
    }

    public void setElbow(int elbow) {
        this.elbow = elbow;
    }

    public int getWrist() {
        return wrist;
    }

    public void setWrist(int wrist) {
        this.wrist = wrist;
    }

    public int getWristRotation() {
        return wristRotation;
    }

    public void setWristRotation(int wristRotation) {
        this.wristRotation = wristRotation;
    }

    public int getDelay() {
        return delay;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }

    public int getGripper() {
        return gripper;
    }

    public void setGripper(int gripper) {
        this.gripper = gripper;
    }

    @Transient
    public byte[] getBytes() {
        return new byte[] { (byte) delay, (byte) base, (byte) shoulder, (byte) elbow, (byte) wrist, (byte) wristRotation, (byte) gripper };
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {

        private BraccioCommand braccioCommand;

        private Builder() {
            braccioCommand = new BraccioCommand(20, DEFAULT_ANGLE, DEFAULT_ANGLE, DEFAULT_ANGLE, DEFAULT_ANGLE, DEFAULT_ANGLE, DEFAULT_GRIPPER);
        }

        public Builder withDelay(int delay) {
            braccioCommand.setDelay(delay);
            return this;
        }

        public Builder withBase(int base) {
            braccioCommand.setBase(base);
            return this;
        }

        public Builder withShoulder(int shoulder) {
            braccioCommand.setShoulder(shoulder);
            return this;
        }

        public Builder withElbow(int elbow) {
            braccioCommand.setElbow(elbow);
            return this;
        }

        public Builder withWrist(int wrist) {
            braccioCommand.setWrist(wrist);
            return this;
        }

        public Builder withWristRotation(int wristRotation) {
            braccioCommand.setWristRotation(wristRotation);
            return this;
        }

        public Builder withGripper(int gripper) {
            braccioCommand.setGripper(gripper);
            return this;
        }

        public BraccioCommand build() {
            return braccioCommand;
        }
    }
}
