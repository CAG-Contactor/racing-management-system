Pi Sensor
==================
Bygga och köra
--------------
1. mvn clean install
2. scp target/pi-1.0-SNAPSHOT-jar-with-dependencies.jar pi@192.168.198.100:/home/pi
3. ssh pi@169.254.230.17 [password:raspberry]
3. sudo java -cp pi-1.0-SNAPSHOT-jar-with-dependencies.jar TimerSensor

Events
-------------------
* START
* SPLIT
* FINISH

Swagger Documentation
---------------------
Finns på <host>/swagger-ui.html




