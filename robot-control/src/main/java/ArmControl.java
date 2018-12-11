import processing.core.PVector;

import java.util.ArrayList;

public class ArmControl {
    private ArmSegment armSegmentFirst;
    private ArmSegment armSegmentSecond;
    private ArmSegment armSegmentThird;
    private PVector rotationPlane;


    public ArmControl() {
        armSegmentFirst = new ArmSegment(0F,68F,124F,165,15,0,0);
        armSegmentSecond = new ArmSegment(armSegmentFirst, 124F,180,0,0,0);
        armSegmentThird = new ArmSegment(armSegmentSecond, 192F,180,0,0,0);
        System.out.println("start! 1:" + armSegmentFirst.toString());
        System.out.println("start! 2:" + armSegmentSecond.toString());
        System.out.println("start! 3:" + armSegmentThird.toString());
    }

    public ArrayList<Float> getNewJointAngles(Float X, Float Y, Float Z) {
        PVector newTarget = new PVector(X, Y);
        for (int i=0; i<20; i++) {
            System.out.println("runda " + i);
            armSegmentThird.backwardCalculation(newTarget);
            armSegmentSecond.backwardCalculation(armSegmentThird.getStartPoint());
            armSegmentFirst.backwardCalculation(armSegmentSecond.getStartPoint());
            armSegmentFirst.forwardCalculation(new PVector(0,68F));
            armSegmentSecond.forwardCalculation(armSegmentFirst.getEndPoint());
            armSegmentThird.forwardCalculation(armSegmentSecond.getEndPoint());
            System.out.println(armSegmentFirst.toString());
            System.out.println(armSegmentSecond.toString());
            System.out.println(armSegmentThird.toString());
            System.out.println("diff to target: " + armSegmentThird.calculateDiffToTarget(newTarget));
            if(armSegmentThird.calculateDiffToTarget(newTarget) < 0.3F) {
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
