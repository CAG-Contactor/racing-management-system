import processing.core.PVector;

import java.util.ArrayList;

public class ArmControl {
    ArmSegment armSegmentFirst;
    ArmSegment armSegmentSecond;
    ArmSegment armSegmentThird;

    public ArmControl() {
        armSegmentFirst = new ArmSegment(0F,68F,124F);
        armSegmentSecond = new ArmSegment(armSegmentFirst, 124F);
        armSegmentThird = new ArmSegment(armSegmentSecond, 192F);
    }

    public ArrayList<Float> getNewJointAngles(Float X, Float Y, Float Z) {
        PVector newTarget = new PVector(X, Y);
        for (int i=0; i<10; i++) {
            armSegmentThird.backwardCalculation(newTarget);
            armSegmentSecond.backwardCalculation(armSegmentThird.getStartPoint());
            armSegmentFirst.backwardCalculation(armSegmentSecond.getStartPoint());
            armSegmentFirst.forwardCalculation(new PVector(0,68F));
            armSegmentSecond.forwardCalculation(armSegmentFirst.getEndPoint());
            armSegmentThird.forwardCalculation(armSegmentSecond.getEndPoint());
            if(armSegmentThird.calculateDiffToTarget(newTarget) < 0.2F) {
                break;
            }
        }

        ArrayList<Float> newAngles = new ArrayList();
        newAngles.add(armSegmentFirst.getAngleInDeg());
        newAngles.add(armSegmentSecond.getAngleInDeg());
        newAngles.add(armSegmentThird.getAngleInDeg());

        return newAngles;
    }
}
