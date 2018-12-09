package se.cag.jfokus.braccio.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import se.cag.jfokus.braccio.serial.AsyncSerialService;
import se.cag.jfokus.braccio.data.BraccioCommand;
import se.cag.jfokus.braccio.data.BraccioResponse;

import java.util.concurrent.CompletableFuture;

@RestController
public class BraccioResource {

    @Autowired
    private AsyncSerialService serialComm;

    @RequestMapping(path = "braccio/move", method = RequestMethod.POST)
    public CompletableFuture<BraccioResponse> doControl(@RequestBody BraccioCommand command) {
        return serialComm.writeData(command);
    }

    @RequestMapping(path = "braccio/reset", method = RequestMethod.GET)
    public CompletableFuture<BraccioResponse> doDefault() {
        BraccioCommand command = BraccioCommand.defaultPosition;
        return serialComm.writeData(command);
    }

}
