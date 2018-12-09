package se.cag.jfokus.braccio.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import se.cag.jfokus.braccio.data.BraccioCommand;
import se.cag.jfokus.braccio.serial.AsyncSerialService;

@Component
public class DemoMoves {

    //The braccio moves to the sponge. Only the M2 servo will moves
    private BraccioCommand pos2 = new BraccioCommand(20,           0,  100, 0, 0,  90,   10);

    //Close the gripper to take the sponge. Only the M6 servo will moves
    private BraccioCommand pos3 = new BraccioCommand(10,           0,  100, 0, 0,  90,  60 );

    //Brings the sponge upwards.
    private BraccioCommand pos4 = new BraccioCommand(20,         0,   140, 0,  40,  0, 60);

    //Show the sponge. Only the M1 servo will moves
    private BraccioCommand pos5 = new BraccioCommand(20,         140,  140, 0,   40,   0,  60);

    //Return to the start position.
    private BraccioCommand pos6 = new BraccioCommand(20,         0,   100, 0,  0,  90, 60);

    //Open the gripper
    private BraccioCommand pos7 = new BraccioCommand(20,         0,   100, 0,  0,  90, 10 );

    @Autowired
    private AsyncSerialService service;


    public void sponge() {
        try {
            service.writeData(BraccioCommand.defaultPosition);
            Thread.sleep(500);
            service.writeData(pos2);
            service.writeData(pos3);
            service.writeData(pos4);
            service.writeData(pos5);
            service.writeData(pos6);
            service.writeData(pos7);
            Thread.sleep(200);
            service.writeData(BraccioCommand.defaultPosition);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public void base() {
        try {
            service.writeData(BraccioCommand.defaultPosition);
            Thread.sleep(500);
            service.writeData(BraccioCommand.builder().withBase(0).build());
            Thread.sleep(200);
            service.writeData(BraccioCommand.builder().withBase(180).build());
            Thread.sleep(200);
            service.writeData(BraccioCommand.defaultPosition);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void shoulder() {
        try {
            service.writeData(BraccioCommand.defaultPosition);
            Thread.sleep(500);
            service.writeData(BraccioCommand.builder().withShoulder(15).build());
            Thread.sleep(200);
            service.writeData(BraccioCommand.builder().withShoulder(165).build());
            Thread.sleep(200);
            service.writeData(BraccioCommand.defaultPosition);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void elbow() {
        try {
            service.writeData(BraccioCommand.defaultPosition);
            Thread.sleep(500);
            service.writeData(BraccioCommand.builder().withElbow(0).build());
            Thread.sleep(200);
            service.writeData(BraccioCommand.builder().withElbow(180).build());
            Thread.sleep(200);
            service.writeData(BraccioCommand.defaultPosition);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void wrist() {
        try {
            service.writeData(BraccioCommand.defaultPosition);
            Thread.sleep(500);
            service.writeData(BraccioCommand.builder().withWrist(0).build());
            Thread.sleep(200);
            service.writeData(BraccioCommand.builder().withWrist(180).build());
            Thread.sleep(200);
            service.writeData(BraccioCommand.defaultPosition);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void wristRotation() {
        try {
            service.writeData(BraccioCommand.defaultPosition);
            Thread.sleep(500);
            service.writeData(BraccioCommand.builder().withWristRotation(0).build());
            Thread.sleep(200);
            service.writeData(BraccioCommand.builder().withWristRotation(180).build());
            Thread.sleep(200);
            service.writeData(BraccioCommand.defaultPosition);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void gripper() {
        try {
            service.writeData(BraccioCommand.defaultPosition);
            Thread.sleep(500);
            service.writeData(BraccioCommand.builder().withGripper(10).build());
            Thread.sleep(200);
            service.writeData(BraccioCommand.builder().withGripper(73).build());
            Thread.sleep(200);
            service.writeData(BraccioCommand.defaultPosition);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
