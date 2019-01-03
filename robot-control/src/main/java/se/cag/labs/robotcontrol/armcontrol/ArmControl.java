package se.cag.labs.robotcontrol.armcontrol;

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
        if(!Float.isNaN(wrist.getEndPoint().x) || !Float.isNaN(wrist.getEndPoint().y)){
            System.out.println(" init med Number :)");
        }
    }

    public ArrayList<Float> getNewJointAngles(Float X, Float Y, Float Z, Float grip) {
        X *=2;
        Y *=2;
        PVector newPosition = new PVector(X,Y,Z);
        checkConstraintCoordnates(newPosition);
        float yzAngle = calculateBaseAngle(newPosition.y, newPosition.z);
        PVector newTarget = new PVector(newPosition.x,calculateNewCoordinates(newPosition.y,yzAngle));
//        if(!Float.isNaN(wrist.getEndPoint().x) || !Float.isNaN(wrist.getEndPoint().y)){
//            System.out.println(" inna Number :)");
//        }
        for (int i=0; i<20; i++) {
//            System.out.println("runda " + i);
            wrist.backwardCalculation(newTarget);

            elbow.backwardCalculation(wrist.getStartPoint());
            shoulder.backwardCalculation(elbow.getStartPoint());
            shoulder.forwardCalculation(new PVector(0,68F));
            elbow.forwardCalculation(shoulder.getEndPoint());
            wrist.forwardCalculation(elbow.getEndPoint());
//            System.out.println(shoulder.toString());
//            System.out.println(elbow.toString());
//            System.out.println(wrist.toString());
//            System.out.println("diff to target: " + wrist.calculateDiffToTarget(newTarget));
            if(wrist.calculateDiffToTarget(newTarget) < 0.3F) {
                break;
            }
        }

        ArrayList<Float> newAngles = new ArrayList();
        newAngles.add(getRelativeRoationAngleInDeg(yzAngle));
        newAngles.add(shoulder.getRelativeAngleInDeg());
        newAngles.add(elbow.getRelativeAngleInDeg());
        newAngles.add(wrist.getRelativeAngleInDeg());

        if(!Float.isNaN(wrist.getEndPoint().x) || !Float.isNaN(wrist.getEndPoint().y)){
            System.out.print("Number :)");
        }

        System.out.print("\r");
        String out = wrist.toString();
        System.out.print(X + "|" + Y + "|"  + Z + "|"  + shoulder.getEndPoint() + elbow.getEndPoint() + wrist.getEndPoint());
//        System.out.print("height:" + X + " | " + wrist.getEndPoint().x + " y-pos:" + Y + " | " + wrist.getEndPoint().y  + " z-pos" + Z + " | " + wrist.getEndPoint().z  + " Pinch: " + grip);
//        System.out.print("height:" + X + " | " + newTarget.x + " y-pos:" + Y + " | " + newTarget.y  + " z-pos" + Z + " | " + newTarget.z  + " Pinch: " + grip);
        //armControl.getNewJointAngles(height,-z,x,pinch);
        return newAngles;
    }

    private float calculateBaseAngle(Float Y, Float Z) {
        float yzAngle = (float)Math.atan(Z/Y);
//        System.out.println("ny vinkel: " + Math.toDegrees(yzAngle));
        return yzAngle;
    }

    private void checkConstraintCoordnates(PVector newPosition) {
        if(newPosition.x < 1F){
            newPosition.x=Float.valueOf(1F);
        }

        if(newPosition.y < 1F){
            newPosition.y=Float.valueOf(1F);
        }

        if(newPosition.z < 0.1f && newPosition.z > -0.1f){
            newPosition.z = 0.1f;
        }

    }

    private float calculateNewCoordinates(float yPosition, float yzAngle) {
        float newY = (float)(yPosition / Math.cos(yzAngle));

        if(newY<=0){
            //System.out.println("ny skitigt y-koordinat");
            newY = 10f;
        }

        return newY;
    }

    private float getRelativeRoationAngleInDeg(float absoluteYZAngle) {
        return (float)Math.toDegrees(absoluteYZAngle + Math.PI / 2);
    }
}
