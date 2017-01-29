Pi Sensor
=========

Lyssnar på GPIO-portar på en Raspberry Pi och registrerar händelser till en central server.

När applikationen startar kommer den försöka hämta hem certifikaten till den centrala servern och registrera sig själv med sin IP-address. 

Därefter börjar den lyssna efter händelser på dessa portar och skickar dem till den centrala servern
* START 
* SPLIT
* FINISH

Installera Rasperry Pi
-------------------------------------
1. mvn clean package
2. scp target/pi-sensor-0.0.1-SNAPSHOT-exec.jar pi@192.168.198.100: [password:raspberry]
3. ssh pi@169.254.230.17 [password:raspberry]
4. sudo apt-get install openjdk-8-jdk
5. sudo nano /etc/systemd/system/pisensor.service
```
[Unit]
Description=pisensor
After=syslog.target
 
[Service]
User=root
ExecStart=/home/pi/pi-sensor-0.0.1-SNAPSHOT-exec.jar
SuccessExitStatus=143
 
[Install]
WantedBy=multi-user.target
```
6. sudo systemctl enable pisensor
7. sudo service pisensor start


Bygga och uppdatera mjukvara
----------------------------
1. mvn clean package
2. scp target/pi-sensor-0.0.1-SNAPSHOT-exec.jar pi@192.168.198.100: [password:raspberry]
3. ssh pi@169.254.230.17 [password:raspberry]
4. sudo service pisensor restart


