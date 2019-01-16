Pi Sensor
=========

Lyssnar på GPIO-portar på en Raspberry Pi och registrerar händelser till en central server.

```
 +-----+-----+---------+------+---+---Pi 3---+---+------+---------+-----+-----+
 | BCM | wPi |   Name  | Mode | V | Physical | V | Mode | Name    | wPi | BCM |
 +-----+-----+---------+------+---+----++----+---+------+---------+-----+-----+
 |     |     |    3.3v |      |   |  1 || 2  |   |      | 5v      |     |     |
 |   2 |   8 |   SDA.1 |   IN | 1 |  3 || 4  |   |      | 5v      |     |     |
 |   3 |   9 |   SCL.1 |   IN | 1 |  5 || 6  |   |      | 0v      |     |     |
 |   4 |   7 | GPIO. 7 |   IN | 1 |  7 || 8  | 0 | IN   | TxD     | 15  | 14  |
 |     |     |      0v |      |   |  9 || 10 | 1 | IN   | RxD     | 16  | 15  |
 |  17 |   0 | GPIO. 0 |   IN | 0 | 11 || 12 | 0 | IN   | GPIO. 1 | 1   | 18  |
 |  27 |   2 | GPIO. 2 |   IN | 0 | 13 || 14 |   |      | 0v      |     |     |
 |  22 |   3 | GPIO. 3 |   IN | 0 | 15 || 16 | 0 | IN   | GPIO. 4 | 4   | 23  |
 |     |     |    3.3v |      |   | 17 || 18 | 0 | IN   | GPIO. 5 | 5   | 24  |
 |  10 |  12 |    MOSI |   IN | 0 | 19 || 20 |   |      | 0v      |     |     |
 |   9 |  13 |    MISO |   IN | 0 | 21 || 22 | 0 | IN   | GPIO. 6 | 6   | 25  |
 |  11 |  14 |    SCLK |   IN | 0 | 23 || 24 | 1 | IN   | CE0     | 10  | 8   |
 |     |     |      0v |      |   | 25 || 26 | 1 | IN   | CE1     | 11  | 7   |
 |   0 |  30 |   SDA.0 |   IN | 1 | 27 || 28 | 1 | IN   | SCL.0   | 31  | 1   |
 |   5 |  21 | GPIO.21 |   IN | 1 | 29 || 30 |   |      | 0v      |     |     |
 |   6 |  22 | GPIO.22 |   IN | 1 | 31 || 32 | 0 | IN   | GPIO.26 | 26  | 12  |
 |  13 |  23 | GPIO.23 |   IN | 0 | 33 || 34 |   |      | 0v      |     |     |
 |  19 |  24 | GPIO.24 |   IN | 0 | 35 || 36 | 0 | IN   | GPIO.27 | 27  | 16  |
 |  26 |  25 | GPIO.25 |   IN | 0 | 37 || 38 | 0 | IN   | GPIO.28 | 28  | 20  |
 |     |     |      0v |      |   | 39 || 40 | 0 | IN   | GPIO.29 | 29  | 21  |
 +-----+-----+---------+------+---+----++----+---+------+---------+-----+-----+
 | BCM | wPi |   Name  | Mode | V | Physical | V | Mode | Name    | wPi | BCM |
 +-----+-----+---------+------+---+---Pi 3---+---+------+---------+-----+-----+
```

Pinnar med udda nummer är närmast broadcom-chippet

```
START = GPIO. 0
SPLIT = GPIO. 2
FINISH = GPIO. 3
```

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
