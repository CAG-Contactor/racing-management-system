import processing.core.PVector;

import static java.lang.StrictMath.cos;
import static java.lang.StrictMath.sin;


public class ArmSegment {

    private PVector startPoint;
    private PVector endPoint = new PVector();
    private Float segmentLength;
    private ArmSegment child;
    private ArmSegment parent;
    private float absoluteAngle = (float)3.14/4;
    private float relativeAngle;

    protected ArmSegment(float x, float y, float length){
        startPoint = new PVector( x,y);

        segmentLength = length;
        calculateEndPoint();
        relativeAngle = absoluteAngle;

    }

    protected ArmSegment(ArmSegment parentSegment, Float length) {
        parent = parentSegment;
        startPoint = new PVector(parent.endPoint.x, parent.endPoint.y) ;
        segmentLength = length;
        calculateEndPoint();
        relativeAngle = parentSegment.absoluteAngle - absoluteAngle;

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

    protected Float getAngleInRad() {
        return relativeAngle;
    }

    protected Float getAngleInDeg() {
        return (float)Math.toDegrees(relativeAngle);
    }

    protected PVector backwardCalculation(PVector newEndpoint) {
        //returnerar startpositionen för vektorn
        PVector newDirectionVector = PVector.sub(newEndpoint, startPoint);
        absoluteAngle = newDirectionVector.heading();
        newDirectionVector.setMag(segmentLength);
        endPoint.set(newEndpoint);
        return PVector.sub(newEndpoint, newDirectionVector, startPoint);
    }

    protected PVector forwardCalculation(PVector newStartPoint) {
        //returnerar slutpositionen vektorn
        PVector diff = PVector.sub(newStartPoint, startPoint);
        startPoint.set(newStartPoint);
        if(parent != null){
             relativeAngle = parent.absoluteAngle - absoluteAngle;
        } else {
            relativeAngle = absoluteAngle;
        }
        return PVector.add(endPoint,diff, endPoint);
    }

    protected Float calculateDiffToTarget(PVector newEndpoint) {
        return PVector.sub(newEndpoint, endPoint).mag();
    }
}
