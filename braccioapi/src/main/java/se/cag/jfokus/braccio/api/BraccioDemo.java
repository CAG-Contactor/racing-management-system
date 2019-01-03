package se.cag.jfokus.braccio.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import se.cag.jfokus.braccio.demo.DemoMoves;

@RestController
public class BraccioDemo {

    private DemoMoves demoMoves;

    @Autowired
    public void setDemoMoves(DemoMoves demoMoves) {
        this.demoMoves = demoMoves;
    }

    @RequestMapping(path = "braccio/demo/sponge", method = RequestMethod.GET)
    public void doSponge() {
        demoMoves.sponge();
    }

    @RequestMapping(path = "braccio/demo/base", method = RequestMethod.GET)
    public void doBase() {
        demoMoves.base();
    }

    @RequestMapping(path = "braccio/demo/shoulder", method = RequestMethod.GET)
    public void doShoulder() {
        demoMoves.shoulder();
    }

    @RequestMapping(path = "braccio/demo/elbow", method = RequestMethod.GET)
    public void doElbow() {
        demoMoves.elbow();
    }

    @RequestMapping(path = "braccio/demo/wrist", method = RequestMethod.GET)
    public void doWrist() {
        demoMoves.wrist();
    }

    @RequestMapping(path = "braccio/demo/wristrotation", method = RequestMethod.GET)
    public void doWristRotation() {
        demoMoves.wristRotation();
    }

    @RequestMapping(path = "braccio/demo/gripper", method = RequestMethod.GET)
    public void doGripper() {
        demoMoves.gripper();
    }

}
