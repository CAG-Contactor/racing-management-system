CAG Racing Management System
============================
The ultimate racing management system :stuck_out_tongue_winking_eye:.

Specification
----
See [Wiki](https://github.com/CAG-Contactor/racing-management-system/wiki).

Mongo DB
--------
Install MongoDB: https://docs.mongodb.org/manual/installation/

MongoDB GUI, 3T MongoChef: http://3t.io/mongochef/download/

Crate a folder for the database. Example:
```
sudo mkdir -p /usr/local/data/db
sudo chmod 757 /usr/local/data/db
```

Start mongod:

    $ mongod --dbpath <path to DB>

Run back-end services with Docker 
---------------
You can use the maven profile ```docker``` to generate docker containers for each server application. 
Run commands in each directory for each app you want to start, ie 

Start Docker on your computer berfore executing following command

```sh
mvn clean install -Pdocker
```


The easiest way to run the docker containers is to start them with docker-compose, simply run

```sh
docker-compose up
```

The [client-api](./client-api/README.md) will then be available at [localhost:10580]() and the swagger documentation at [localhost:10580/swagger-ui.html]().

See also [Ports](#ports). 

To remove unused images run the following:
```sh
docker rmi $(docker images | grep "^<none>" | awk '{print $3}')
```

Tools
-----

- [Postman: REST test tool](https://chrome.google.com/webstore/detail/postman/fhbjgbiflinjbdggehcddcbncdddomop?hl=en)
- [Simulator for detector (raspberry pi)](./detector-sim/README.md) 

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

- [cag-rms-client-webpack](./cag-rms-client-webpack/README.md): The Angular 1.x based web client for the application
- [client-api](./client-api/README.md): The REST-API facade towards other back-end services used by the cag-rms-client
- [race-administrator](./race-administrator/README.md): The service handling user queueing and orchestration of races
- [current-race](./current-race/README.md): The service handling and supervising the currently ongoing race and receiving detector passage events
- [leaderboard](./leaderboard/README.md): The service keeping track of all results
- [user-manager](./user-manager/README.md): The service keeping track of registered users and active sessions

<a name="ports">Ports</a>
------
* current-race: 10080
* leaderboard: 10180
* user-manager: 10280
* race-administrator: 10380
* client-api: 10580

Time measurement
----------------
Foto resistor spec: https://tkkrlab.nl/wiki/Arduino_KY-018_Photo_resistor_module
