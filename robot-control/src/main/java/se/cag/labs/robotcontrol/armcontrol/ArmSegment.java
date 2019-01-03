package se.cag.labs.robotcontrol.armcontrol;

import processing.core.PVector;

import static java.lang.StrictMath.cos;
import static java.lang.StrictMath.sin;
import static java.lang.StrictMath.tan;


public class ArmSegment {

    private PVector startPoint;
    private PVector endPoint = new PVector();
    private Float segmentLength;
    private ArmSegment child;
    private ArmSegment parent;
    private float absoluteAngle = (float)3.14/4;
    private float relativeAngle;
    private Float constraintXtoYUp;
    private Float constraintXtoYDown;
    private Float constraintYtoZLeft;
    private Float constraintYtoZRight;

    protected ArmSegment(float x, float y, float z, float length, float constraintXYUpInDeg, float constraintXYDownInDeg){
        startPoint = new PVector( x,y,z);

        segmentLength = length;
        calculateEndPoint();
        relativeAngle = absoluteAngle;
        constraintXtoYUp = (float)Math.toRadians(constraintXYUpInDeg);
        constraintXtoYDown = (float)Math.toRadians(constraintXYDownInDeg);
//        System.out.println("constraint angles up: " + constraintXYUpInDeg  + " down: " + constraintXYDownInDeg);
//        constraintXtoYUp = (float)Math.toDegrees(constraintXYUpInDeg);
//        constraintXtoYDown = (float)Math.toDegrees(constraintXYDownInDeg);
//        constraintYtoZLeft = (float)Math.toDegrees(constraintYZLeftInDeg);
//        constraintYtoZRight = (float)Math.toDegrees(constraintYZRightInDeg);

    }

    protected ArmSegment(ArmSegment parentSegment, Float length, float constraintXYUpInDeg, float constraintXYDownInDeg) {
        parent = parentSegment;
        parentSegment.child = this;
        startPoint = new PVector(parent.endPoint.x, parent.endPoint.y, parent.endPoint.z) ;
        segmentLength = length;
        calculateEndPoint();
        relativeAngle = parentSegment.absoluteAngle - absoluteAngle;
        constraintXtoYUp = (float)Math.toRadians(constraintXYUpInDeg);
        constraintXtoYDown = (float)Math.toRadians(constraintXYDownInDeg);

//        System.out.println("constraint angles up: " + constraintXYUpInDeg  + " down: " + constraintXYDownInDeg);
//        constraintXtoYUp = (float)Math.toDegrees(constraintXYUpInDeg);
//        constraintXtoYDown = (float)Math.toDegrees(constraintXYDownInDeg);
//        constraintYtoZLeft = (float)Math.toDegrees(constraintYZLeftInDeg);
//        constraintYtoZRight = (float)Math.toDegrees(constraintYZRightInDeg);

    }

    private void setupConstraints() {
        Float lengthXYup = segmentLength * (float)tan(constraintXtoYUp);
        Float lengthXYdown = segmentLength * (float)tan(constraintXtoYDown);
        Float lengthYZLeft = segmentLength * (float)tan(constraintYtoZLeft);
        Float lengthYZRight = segmentLength * (float)tan(constraintYtoZRight);
    }

    protected void calculateEndPoint() {
        float dx = segmentLength * (float)cos(absoluteAngle);
        float dy = segmentLength * (float)sin(absoluteAngle);
        endPoint.set(startPoint.x + dx, startPoint.y+dy);
    }


    protected PVector getStartPoint() {
        return startPoint;
    }

    protected PVector getEndPoint() {
        return endPoint;
    }

    protected Float getAbsoluteAngleInRad() {
        return absoluteAngle;
    }

    protected Float getAbsoluteAngleInDeg() {
        return (float)Math.toDegrees(absoluteAngle);
    }

    protected Float getAbsoluteAngle() {return absoluteAngle;}

    protected Float getAngleInRad() {
        return relativeAngle;
    }

    protected Float getRotationInRad() {
        PVector rotationVector = new PVector(endPoint.y,endPoint.z);
        return rotationVector.heading();
    }

    protected Float getRotationInDeg() {
        return (float)Math.toDegrees(getRotationInRad());
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("startpoint: ");
        sb.append(startPoint);
        sb.append(" | endpoint: ");
        sb.append(endPoint);
        sb.append(" | absolut vinkel: ");
        sb.append(absoluteAngle);
        sb.append(" relativ vinkel: ");
        sb.append(relativeAngle);
        return sb.toString();
    }

    protected Float getRelativeAngleInDeg() {
        return (float)Math.toDegrees(relativeAngle);
    }

    protected PVector backwardCalculation(PVector newEndpoint) {
        //returnerar startpositionen för vektorn
        PVector newDirectionVector = PVector.sub(newEndpoint, startPoint);
        absoluteAngle = newDirectionVector.heading();

        if(parent != null) {
            relativeAngle = parent.absoluteAngle + absoluteAngle;

        } else {
            relativeAngle = absoluteAngle;
        }

        if(relativeAngle > constraintXtoYUp){
            relativeAngle = constraintXtoYUp;
            if(parent != null){
                absoluteAngle = relativeAngle - parent.absoluteAngle;
            } else {
                absoluteAngle = relativeAngle;
            }
            newDirectionVector = PVector.fromAngle(absoluteAngle);
        } else if (relativeAngle < constraintXtoYDown) {
            relativeAngle = constraintXtoYDown;
            if(parent != null){
                absoluteAngle = relativeAngle - parent.absoluteAngle;
            } else {
                absoluteAngle = relativeAngle;
            }
            newDirectionVector = PVector.fromAngle(absoluteAngle);
        }


        newDirectionVector.setMag(segmentLength);
        endPoint.set(newEndpoint);
        //lägg till kontroll på begränsningsvinklar
        //om bägränsning är passerad sätt vinkeln till begränsning
        //och beräkna om ny startpoint för vektorn för att hålla gripklon "bakom" önskad enpoint.
        return PVector.sub(newEndpoint, newDirectionVector, startPoint);
    }

    private void setConstraintAngles(){


    }

    protected PVector forwardCalculation(PVector newStartPoint) {
        //returnerar slutpositionen vektorn
        PVector diff = PVector.sub(newStartPoint, startPoint);
        startPoint.set(newStartPoint);
//        System.out.println("forward start: " + startPoint + endPoint);

        if(parent != null){
             relativeAngle = parent.absoluteAngle + absoluteAngle;
        } else {
            relativeAngle = absoluteAngle;
        }

//        System.out.println("relative: " + Math.toDegrees(relativeAngle) + " constraint UP: " + Math.toDegrees(constraintXtoYUp) + " constraint down: " + Math.toDegrees(constraintXtoYDown));
        if(relativeAngle > constraintXtoYUp){
//            System.out.println("begränsning uppåt");
            relativeAngle = constraintXtoYUp;
            setEndPointByConstraint();
        } else if (relativeAngle < constraintXtoYDown) {
//            System.out.println("begränsning nedåt");
            relativeAngle = constraintXtoYDown;
            setEndPointByConstraint();
        } else {
            //within constraint
//            return PVector.add(endPoint,diff, endPoint);
            PVector.add(endPoint,diff, endPoint);
        }
//        System.out.println("forward end: " + startPoint + endPoint + "\n");
        return endPoint;
    }

    private void setEndPointByConstraint() {
//        System.out.println("angles before relative: " + Math.toDegrees(relativeAngle) + " | absolute: " + Math.toDegrees(absoluteAngle));
        if(parent!=null){
            absoluteAngle = relativeAngle - parent.absoluteAngle;
        } else {
            absoluteAngle = relativeAngle;
        }
//        System.out.println("angles after relative: " + Math.toDegrees(relativeAngle) + " | absolute: " + Math.toDegrees(absoluteAngle));
        PVector newEndpointDirectionVector = PVector.fromAngle(absoluteAngle);
        newEndpointDirectionVector.setMag(segmentLength);
        PVector.add(startPoint,newEndpointDirectionVector,endPoint);
//        System.out.println("new vectors in constraints: " + startPoint + endPoint + newEndpointDirectionVector);
    }

    protected Float calculateDiffToTarget(PVector newEndpoint) {
        return PVector.sub(newEndpoint, endPoint).mag();
    }
}
