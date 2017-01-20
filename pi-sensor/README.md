Pi Sensor
=================================
Bygga och köra
---------------------------------
1.   mvn clean compile assembly:single
2.   scp target/pi-sensor-0.0.1-SNAPSHOT-jar-with-dependencies.jar pi@192.168.198.100: [password:raspberry]
3.   ssh pi@169.254.230.17 [password:raspberry]
4.1  sudo java -jar pi-sensor-0.0.1-SNAPSHOT-jar-with-dependencies.jar
4.2  sudo java -Dserver.uri=https://sumorace.caglabs.se/race/passageDetected -Djavax.net.ssl.trustStore=keystore.jks -jar pi-sensor-0.0.1-SNAPSHOT-jar-with-dependencies.jar

Hämta hem Cert och skapa keystore
---------------------------------
1. Kör följande och plocka PEM-filen: openssl s_client -host sumorace.caglabs.se -port 443
2. Skapa en keystore: keytool -import -alias droidrace -keystore keystore.jks -file droidrace.pem

Events
---------------------------------
* START
* SPLIT
* FINISH

Swagger Documentation
---------------------------------
Finns på <host>/swagger-ui.html




