echo "java -Djava.library.path=/usr/lib/jni -cp "/usr/share/java/RXTXcomm.jar:./braccio-api-1.0-SNAPSHOT.jar" -Dgnu.io.rxtx.SerialPorts=/dev/ttyACM0 se.cag.jfokus.braccio.Braccio"
java -Djava.library.path=/usr/lib/jni -cp "/usr/share/java/RXTXcomm.jar" -Dgnu.io.rxtx.SerialPorts=/dev/ttyACM0 -jar ./braccio-api-1.0-SNAPSHOT.jar 
