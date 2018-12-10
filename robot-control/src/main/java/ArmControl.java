import processing.core.PVector;

import java.util.ArrayList;

public class ArmControl {
    private ArmSegment armSegmentFirst;
    private ArmSegment armSegmentSecond;
    private ArmSegment armSegmentThird;
    private PVector rotationPlane;


    public ArmControl() {
        armSegmentFirst = new ArmSegment(0F,68F,124F,165,15,0,0);
        armSegmentSecond = new ArmSegment(armSegmentFirst, 124F,90,0,0,0);
        armSegmentThird = new ArmSegment(armSegmentSecond, 192F,70,0,0,0);
        System.out.println("start! 1:" + armSegmentFirst.getStartPoint() + " : " + armSegmentFirst.getEndPoint());
        System.out.println("start! 2:" + armSegmentSecond.getStartPoint() + " : " + armSegmentSecond.getEndPoint());
        System.out.println("start! 3:" + armSegmentThird.getStartPoint() + " : " + armSegmentThird.getEndPoint());
        System.out.println("start! first:" + armSegmentFirst.getAbsoluteAngleInDeg() + " second: " + armSegmentSecond.getAbsoluteAngleInDeg() + " third: " + armSegmentThird.getAbsoluteAngleInDeg());
    }

    public ArrayList<Float> getNewJointAngles(Float X, Float Y, Float Z) {
        PVector newTarget = new PVector(X, Y);
        for (int i=0; i<50; i++) {
            System.out.println("runda " + i);
            armSegmentThird.backwardCalculation(newTarget);
            armSegmentSecond.backwardCalculation(armSegmentThird.getStartPoint());
            armSegmentFirst.backwardCalculation(armSegmentSecond.getStartPoint());
            armSegmentFirst.forwardCalculation(new PVector(0,68F));
            armSegmentSecond.forwardCalculation(armSegmentFirst.getEndPoint());
            armSegmentThird.forwardCalculation(armSegmentSecond.getEndPoint());
            System.out.println("diff to target: " + armSegmentThird.calculateDiffToTarget(newTarget));
            if(armSegmentThird.calculateDiffToTarget(newTarget) < 0.2F) {
                break;
            }
        }

        ArrayList<Float> newAngles = new ArrayList();
        newAngles.add(armSegmentFirst.getRelativeAngleInDeg());
        newAngles.add(armSegmentSecond.getRelativeAngleInDeg());
        newAngles.add(armSegmentThird.getRelativeAngleInDeg());

        return newAngles;
    }
}
