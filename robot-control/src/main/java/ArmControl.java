import processing.core.PVector;

import java.util.ArrayList;

public class ArmControl {
    private ArmSegment shoulder;
    private ArmSegment elbow;
    private ArmSegment wrist;
    private float rotationAngle;


    public ArmControl() {
        shoulder = new ArmSegment(0F,68F,0F,124F,165,15);
        elbow = new ArmSegment(shoulder, 124F,180,0);
        wrist = new ArmSegment(elbow, 192F,180,0);
        System.out.println("start! 1:" + shoulder.toString());
        System.out.println("start! 2:" + elbow.toString());
        System.out.println("start! 3:" + wrist.toString());
    }

    public ArrayList<Float> getNewJointAngles(Float X, Float Y, Float Z, Float grip) {
        PVector newPosition = new PVector(X,Y,Z);
        checkConstraintCoordnates(newPosition);
        float yzAngle = calculateBaseAngle(Y, Z);
        PVector newTarget = new PVector(X,calculateNewCoordinates(Y,yzAngle));
        for (int i=0; i<20; i++) {
            System.out.println("runda " + i);
            wrist.backwardCalculation(newTarget);
            elbow.backwardCalculation(wrist.getStartPoint());
            shoulder.backwardCalculation(elbow.getStartPoint());
            shoulder.forwardCalculation(new PVector(0,68F));
            elbow.forwardCalculation(shoulder.getEndPoint());
            wrist.forwardCalculation(elbow.getEndPoint());
            System.out.println(shoulder.toString());
            System.out.println(elbow.toString());
            System.out.println(wrist.toString());
            System.out.println("diff to target: " + wrist.calculateDiffToTarget(newTarget));
            if(wrist.calculateDiffToTarget(newTarget) < 0.3F) {
                break;
            }
        }

        ArrayList<Float> newAngles = new ArrayList();
        newAngles.add(getRelativeRoationAngleInDeg(yzAngle));
        newAngles.add(shoulder.getRelativeAngleInDeg());
        newAngles.add(elbow.getRelativeAngleInDeg());
        newAngles.add(wrist.getRelativeAngleInDeg());

        return newAngles;
    }

    private float calculateBaseAngle(Float Y, Float Z) {
        float yzAngle = (float)Math.atan(Z/Y);
        System.out.println("ny vinkel: " + Math.toDegrees(yzAngle));
        return yzAngle;
    }

    private void checkConstraintCoordnates(PVector newPosition) {
        if(newPosition.x < 0){
            newPosition.x=Float.valueOf(0F);
        } if(newPosition.y < 0){
            newPosition.y=Float.valueOf(0F);
        }

    }

    private float calculateNewCoordinates(float yPosition, float yzAngle) {
        return (float)(yPosition / Math.cos(yzAngle));
    }

    private float getRelativeRoationAngleInDeg(float absoluteYZAngle) {
        return (float)Math.toDegrees(absoluteYZAngle + Math.PI / 2);
    }
}
