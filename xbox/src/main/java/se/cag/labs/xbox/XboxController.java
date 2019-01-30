package se.cag.labs.xbox;
import com.studiohartman.jamepad.ControllerManager;
import com.studiohartman.jamepad.ControllerState;
import org.glassfish.jersey.client.ClientConfig;

import javax.ws.rs.client.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class XboxController {
    static Client client = ClientBuilder.newClient(new ClientConfig()/*.register(LoggingFilter.class)*/);
    static WebTarget webTarget = client.target("http://192.168.198.188:8080/braccio/").path("move");


    public static void main(String[] args) {
        ControllerManager controllerManager = new ControllerManager();
        controllerManager.initSDLGamepad();
        int oldXPos = 0;
        int oldYPos = 0;
        int xPos = 0;
        int yPos = 0;

        while(true) {
            ControllerState currState = controllerManager.getState(0);

            xPos = getServoPos(-1*currState.rightStickX);
            yPos = getServoPos(-1*currState.rightStickY);
            if(Math.abs(xPos - oldXPos) > 5  || Math.abs(yPos - oldYPos) > 5){
//                System.out.println("x: " + currState.rightStickX + " y: " + currState.rightStickY);
//                System.out.println("x: " + getServoPos(currState.rightStickX) + " y: " + getServoPos(currState.rightStickY));
                oldXPos = xPos;
                oldYPos = yPos;
                sendCommand(xPos, yPos);
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {

            }

        }
    }

    static int getServoPos(float pos){
       //return Math.round((1-pos)*90);
       return Math.round(90+pos*60);
    }

    static void sendCommand(int x, int y){
        String json = "{\n" +
                "\"delay\" : 10,\n" +
                "\"base\": 0,\n" +
                "\"shoulder\" : " + y + ",\n" +
                "\"elbow\" : " + x + ",\n" +
                "\"wrist\" : 90,\n" +
                "\"wristRotation\": 90,\n" +
                "\"gripper\" : 50\n" +
                "}";

        Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON_TYPE);
        Response response = invocationBuilder.post(Entity.entity(json, MediaType.APPLICATION_JSON_TYPE));
    }
}
