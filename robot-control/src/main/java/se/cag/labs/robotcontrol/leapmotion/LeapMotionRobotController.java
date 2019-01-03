package se.cag.labs.robotcontrol.leapmotion;

import com.leapmotion.leap.*;
import com.leapmotion.leap.Frame;
import se.cag.labs.robotcontrol.armcontrol.ArmControl;

import java.io.IOException;

class HandListener extends Listener {
    String str = "Height: %d Pos:(%d,%d) Pinch: %d";
    //
    ArmControl armControl = new ArmControl();

    public void onInit(Controller controller) {
        System.out.println("Initialized");
    }

    public void onConnect(Controller controller) {
        System.out.println("Connected");
    }

    public void onDisconnect(Controller controller) {
        //Note: not dispatched when running in a debugger.
        System.out.println("Disconnected");
    }

    public void onExit(Controller controller) {
        System.out.println("Exited");
    }

    public void onFrame(Controller controller) {
        float height = 0;
        float z = 0;
        float x = 0;
        float y = 0;
        float pinch = 0;
        // Get the most recent frame and report some basic information
        Frame frame = controller.frame();
//        System.out.println("Frame id: " + frame.id()
//                + ", timestamp: " + frame.timestamp()
//                + ", hands: " + frame.hands().count()
//                + ", fingers: " + frame.fingers().count()
//                + ", tools: " + frame.tools().count()
//                + ", gestures " + frame.gestures().count());

        //Get hands
        for (Hand hand : frame.hands()) {
            // Left hand controls height
            if (hand.isLeft()) {
                height = hand.palmPosition().getY();
            }
            else if (hand.isRight()) {
                x = hand.palmPosition().getX();
                z = hand.palmPosition().getZ();
                y = hand.palmPosition().getY();
                pinch = hand.pinchStrength();
            }
        }
//        System.out.print("\r");
//        System.out.print("Leap motion height:" + height + " x-pos:" + x + " z-pos" + z + " Pinch: " + pinch);
        armControl.getNewJointAngles(height,-z,1F,pinch);
//        armControl.getNewJointAngles(height,-z,x,pinch);
    }
}

class LeapMotionRobotController {
    public static void main(String[] args) {
        HandListener listener = new HandListener();
        Controller controller = new Controller();



        controller.addListener(listener);

        System.out.println("Press Enter to quit...");
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }

        controller.removeListener(listener);
    }
}
