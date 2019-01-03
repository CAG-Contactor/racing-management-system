package se.cag.jfokus.braccio.serial;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;
import se.cag.jfokus.braccio.data.BraccioCommand;
import se.cag.jfokus.braccio.data.BraccioResponse;

import java.util.concurrent.CompletableFuture;

@Service
public class AsyncSerialService {

    @Autowired
    private TaskExecutor taskExecutor;
    @Autowired
    private UnoSerialComm serialComm;

    public CompletableFuture<BraccioResponse> writeData(BraccioCommand command) {
        CompletableFuture<BraccioResponse> response = serialComm.write(command.getBytes());
        if (! response.isDone() && ! response.isCancelled()) {
            taskExecutor.execute(() -> serialComm.read(response));
        }
        return response;
    }
}
