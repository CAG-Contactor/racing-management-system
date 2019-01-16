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

wiringPi libbet måste kompileras lokalt på pajen:
=================================================

    sudo apt install git-core
    cd
    git clone git://git.drogon.net/wiringPi
    cd wiringPi/
    git pull origin
    ./build
    
test:

    gpio -v
    gpio readall


pi4j måste också installeras lokalt på pajen:
==============================================

    wget http://get.pi4j.com/download/pi4j-1.2-SNAPSHOT.deb
    sudo dpkg -i pi4j-1.2-SNAPSHOT.deb

Nu kan man bygga och skjuta över javakoden:
===========================================

1. mvn clean package
2. scp target/pi-sensor-0.0.1-SNAPSHOT-exec.jar pi@192.168.198.100: [password:raspberry]
3. ssh pi@169.254.230.17 [password:raspberry]
4. sudo apt-get install openjdk-8-jdk
5. sudo nano /etc/systemd/system/pisensor.service
```
[Unit]
Description=pisensor
Wants=network-online.target
After=network-online.target

[Service]
User=root
ExecStart=/home/pi/pi-sensor-0.0.1-SNAPSHOT-exec.jar
SuccessExitStatus=143
TimeoutStopSec=10
Restart=on-failure
RestartSec=5

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

Braccio REST-gränssnitt
=======================
Körs på en egen pi ansluten till robot-arduinon via USB

    sudo nano /etc/systemd/system/pisensor.service

```
[Unit]
Description=braccio
Wants=network-online.target
After=network-online.target

[Service]
User=root
ExecStart=/usr/bin/java -Djava.library.path=/usr/lib/jni -cp "/usr/share/java/RXTXcomm.jar" -Dgnu.io.rxtx.SerialPorts=/dev/ttyACM0 -jar /home/pi/braccio-api-1.0-SNAPSHOT.jar
Type=Simple
User=pi

[Install]
WantedBy=multi-user.target```

    sudo systemctl enable braccio
    sudo service braccio start
