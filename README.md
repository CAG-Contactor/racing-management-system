CAG Racing Management System
==========================
The ultimate racing management system.

Spec
----
https://docs.google.com/document/d/1U0zHYvEPcVbiDV0DH9tzJArNnOec-c7YbB-wD7Sztss/edit?usp=sharing

Mongo DB
--------
Install MongoDB: https://docs.mongodb.org/manual/installation/

MongoDB GUI, 3T MongoChef: http://3t.io/mongochef/download/

Start mongod:

    $ mongod --dbpath <path to DB>

Run with Docker 
---------------
You can use the maven profile ```docker``` to generate docker containers for each server application. 

```sh
mvn clean install -Pdocker
```

The easiest way to run the docker containers is to start them with docker-compose, simply run

```sh
docker-compose up
```

To remove unsued images run the following:
```sh
docker rmi $(docker images | grep "^<none>" | awk '{print $3}')
```

Tools
-----
REST Test tool:<br>
Postman: https://chrome.google.com/webstore/detail/postman/fhbjgbiflinjbdggehcddcbncdddomop?hl=en

Build from root pom.xml:
------------------------
Without integration-tests (doesn't require mongod installed locally):

```sh
mvn package
```

With integration-tests (requires local mongod running on port 27017):

```sh
mvn install
```

To clean all generated files from node.js in the client, use the profile clean-node:

```sh
mvn -Pclean-node clean
```

Submodules
----------

- cag-rms-client: The Angular 1.x based web client for the application
- client-api: The REST-API facade towards other back-end services used by the cag-rms-client
- race-administrator: The service handling user queueing and orchestration of races
- current-race: The service handling and supervising the currently ongoing race and receiving detector passage events
- leaderboard: The service keeping track of all results
- user-manager: The service keeping track of registered users and active sessions

Ports
------
* current-race: 10080
* leaderboard: 10180
* user-manager: 10280
* race-administrator: 10380
* client-api: 10580

Time measurement
----------------
Foto resistor spec: https://tkkrlab.nl/wiki/Arduino_KY-018_Photo_resistor_module
