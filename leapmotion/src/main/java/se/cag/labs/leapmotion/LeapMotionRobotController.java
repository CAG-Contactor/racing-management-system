package se.cag.labs.leapmotion;

import com.leapmotion.leap.*;
import com.leapmotion.leap.Frame;

import javax.ws.rs.client.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.filter.LoggingFilter;


import javax.ws.rs.client.Client;
import java.io.IOException;

class HandListener extends Listener {
    String str = "Height: %d Pos:(%d,%d) Pinch: %d";

    static Client client = ClientBuilder.newClient(new ClientConfig()/*.register(LoggingFilter.class)*/);
    static WebTarget webTarget = client.target("http://192.168.198.188:8080/braccio/").path("move");


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
        float leftController = 0;
        float rightController = 0;
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
        boolean leftValid = false;
        boolean rightValid = false;
        for (Hand hand : frame.hands()) {
            // Left hand controls height
            if (hand.isLeft()) {
                leftController = hand.palmPosition().getY();
                leftValid = hand.isValid();
            } else if (hand.isRight()) {
                x = hand.palmPosition().getX();
                rightValid = hand.isValid();
                rightController = hand.palmPosition().getY();
                y = hand.palmPosition().getY();
                pinch = hand.pinchStrength();

            }
        }
//        System.out.println("palm left: " + leftController + " right: " + rightController);
        if(leftValid && rightValid){
            //System.out.println("left: " + leftController + " right: " + rightController);
            if(sendCmd((int) ((leftController - 250) * .6) + 90,
                    (int) ((rightController - 250) * .6) + 90)) {
                System.out.println("palm left: " + leftController + " right: " + rightController);
                System.out.println("left" + leftValid + " right: " + rightValid);
                System.out.println("_____________________________");
            }

        }
    }

    int lastLeft = 90;
    int lastRight = 90;
    int ctr = 0;
    long lastCmdTime = 0;

    private boolean sendCmd(int left, int right) {
        //System.out.println(">> left: " + left + " right: " + right);
        left = (int)(Math.min(Math.max(30, left), 150));
        right = (int) (Math.min(Math.max(30, right), 150));
        // left = 90;
        if (System.currentTimeMillis() - lastCmdTime > 300 && (Math.abs(left - lastLeft) > 5 || Math.abs(right - lastRight) > 5)) {
            lastCmdTime = System.currentTimeMillis();
            lastLeft = left;
            lastRight = right;
            ctr++;
            System.out.println("ctr:" + ctr + " left: " + left + " right: " + right);
            String json = "{\n" +
                    "\"delay\" : 10,\n" +
                    "\"base\": 0,\n" +
                    "\"shoulder\" : " + left + ",\n" +
                    "\"elbow\" : " + right + ",\n" +
                    "\"wrist\" : 90,\n" +
                    "\"wristRotation\": 90,\n" +
                    "\"gripper\" : 50\n" +
                    "}";

            Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON_TYPE);
            Response response = invocationBuilder.post(Entity.entity(json, MediaType.APPLICATION_JSON_TYPE));

            System.out.println(response.getStatus());
//            System.out.println(response.readEntity(String.class));
            return true;
        }
        return false;
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
