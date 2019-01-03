
This contains a Spring Boot REST service to control a Braccio ROBOT.

Service is built for being run on a Raspberry PI 3, on OpenJDK 9 (which 
is the highest supported on Raspberry).

This will be compiled to a FAT JAR with Spring Boot. The RXTX lib needs
to be from the raspberry, to match the library files. 

TO RUN THIS:

1. Install OpenJDK 9 on your raspberry.

    ```sudo apt install openjdk-9-jre```

2. Install librxtx-java on your raspberry

    ```sudo apt-get install librxtx-java```
    
