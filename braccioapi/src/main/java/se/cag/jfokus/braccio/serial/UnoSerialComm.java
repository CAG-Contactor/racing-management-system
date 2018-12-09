package se.cag.jfokus.braccio.serial;

import gnu.io.CommPortIdentifier;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import gnu.io.UnsupportedCommOperationException;
import org.springframework.beans.factory.annotation.Autowired;
import javax.annotation.PostConstruct;
import org.springframework.stereotype.Service;
import se.cag.jfokus.braccio.config.SerialConfig;
import se.cag.jfokus.braccio.data.BraccioResponse;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Logger;

@Service
class UnoSerialComm {

    private static final Logger logger = Logger.getLogger(UnoSerialComm.class.getName());

    private SerialConfig serial;
    private SerialPort serialPort;
    private OutputStream serialOutput;
    private InputStream serialInput;

    @Autowired
    public void setSerial(SerialConfig serial) {
        this.serial = serial;
    }

    @PostConstruct
    public void init() {
        try {
            Enumeration portList = CommPortIdentifier.getPortIdentifiers();
            while (portList.hasMoreElements()) {
                CommPortIdentifier portId = (CommPortIdentifier) portList.nextElement();
                if (portId.getPortType() == CommPortIdentifier.PORT_SERIAL) {
                    if (portId.getName().equals(serial.getName())) {
                        System.out.println("Found port: " + serial.getName());
                        serialPort = (SerialPort) portId.open("SimpleReadApp", 2000);
                        serialPort.setSerialPortParams(serial.getBaud(), SerialPort.DATABITS_8,
                                SerialPort.STOPBITS_1,
                                SerialPort.PARITY_NONE);
                        serialInput = serialPort.getInputStream();
                        serialOutput = serialPort.getOutputStream();
                    }
                }

            }
            if (serialPort == null) {
                throw new IllegalArgumentException("Port " + serial.getName() + " not found!");
            }
        } catch (UnsupportedCommOperationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (PortInUseException e) {
            e.printStackTrace();
        }
    }

    public CompletableFuture<BraccioResponse> write(byte[] bytes) {
            CompletableFuture<BraccioResponse> future = new CompletableFuture<>();
            try {
                logger.fine("DATA serial Send");
                serialOutput.write(bytes);
            } catch (Throwable t) {
                future.complete(new BraccioResponse(false, t.getMessage()));
            }
            return future;
    }

    public void read(CompletableFuture<BraccioResponse> future) {
        try {
            byte[] buf = new byte[1];
            serialInput.read(buf);
            logger.fine("DATA serial Read " + buf[0]);
            future.complete(new BraccioResponse(buf[0] > 0, ""));
        } catch (IOException e) {
            future.complete(new BraccioResponse(false, e.getMessage()));
        }
    }

}
