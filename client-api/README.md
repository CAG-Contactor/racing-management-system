Client API
==========
API-fasad som agerar front mot övriga tjänster i systemet. 
Denna fasad används av [cag-rms-client-webpack](../cag-rms-client-webpack/README.md).

Swagger Documentation
---------------------
Finns på `<host:port>/swagger-ui.html`, t.ex [http://localhost:10580]().

Beskrivning
-------------
Denna tjänst tillhandahåller en API-fasad gentemot övriga tjänster i back-end.

API:et består av ett REST-gränssnitt samt en websocket-ändpunkt via vilken händelser kan tas emot.

Dessutom tillhandahåller den tjänsten att skicka (broadcast) händelse-meddelanden
till alla cag-rms-client-instanser som är uppkopplade.

REST-metoder
-----------------------
### ClientAPI

#### POST /users
Registrera en användare genom att vidarebefordra till `<user-manager>/users`.

##### Begäran
Ett JSON-objekt motsvarande java-klassen [User](../client-api/src/main/java/se/cag/labs/cagrms/clientapi/service/User.java).

#### POST /login
Logga in användare genom att vidarebefordra till `<user-manager>/login`.

##### Begäran
Ett JSON-objekt motsvarande java-klassen [User](../client-api/src/main/java/se/cag/labs/cagrms/clientapi/service/User.java).

#### POST /logout
Logga ut användare genom att vidarebefordra till `<user-manager>/logout?token=<värde från X-AuthToken header>`.

##### Header parameter
X-AuthToken: sessionstoken för inloggad användare.

#### GET /leaderboard
Hämta resultattavla genom att vidarebefordra till `<leaderboard>/results`.

##### Svar
En array med JSON-objekt motsvarande java-klassen [UserResult](../client-api/src/main/java/se/cag/labs/cagrms/clientapi/service/UserResult.java).

#### GET /userqueue
Hämta kön genom att vidarebefordra till (GET) `<race-administrator>/userqueue`.

##### Svar
En array med JSON-objekt motsvarande java-klassen [User](../client-api/src/main/java/se/cag/labs/cagrms/clientapi/service/User.java).

#### POST /userqueue
Anmäl genom att vidarebefordra till (POST) `<race-administrator>/userqueue`.

##### Begäran
Ett JSON-objekt motsvarande java-klassen [User](../client-api/src/main/java/se/cag/labs/cagrms/clientapi/service/User.java).

#### DELETE /userqueue
Avanmäl genom att vidarebefordra till (DELETE) `<race-administrator>/userqueue`.

##### Begäran
Ett JSON-objekt motsvarande java-klassen [User](../client-api/src/main/java/se/cag/labs/cagrms/clientapi/service/User.java).

#### GET /currentrace
Vidarebefordra till (GET) `<race-administrator>/currentrace`.

##### Svar
Ett JSON-objekt motsvarande java-klassen [RaceStatus](../client-api/src/main/java/se/cag/labs/cagrms/clientapi/service/RaceStatus.java).

#### GET /lastrace
Läsa upp status för senast avslutade lopp.

##### Svar
Ett JSON-objekt motsvarande java-klassen [RaceStatus](../client-api/src/main/java/se/cag/labs/cagrms/clientapi/service/RaceStatus.java).

#### POST /reset-race
Avbryt genom att vidarebefordra till `<race-administrator>/reset-race`.

### Extern ändpunkt för att lyssna på händelser
Denna är implementerad med en websocket-ändpunkt med URI:n: `ws://<host>/eventchannel`.

Händelser som tas emot är JSON-objekt motsvarande java-klassen [Event](../race-administrator/src/main/java/se/cag/labs/raceadmin/peerservices/Event.java).
 
Fältet `Event.data` innehåller olika information beroende på `Event.eventType`. 
Följande händelser kan komma via denna kanal:

- `Event.eventType=CURRENT_RACE_STATUS` signalerar uppdatering av status för lopp; `Event.data` är en [RaceStatus](../race-administrator/src/main/java/se/cag/labs/raceadmin/RaceStatus.java)
- `Event.eventType=QUEUE_UPDATED` signalerar uppdatering av kön med anmälda tävlande; `Event.data` är en [User](../race-administrator/src/main/java/se/cag/labs/raceadmin/User.java)
- `Event.eventType=NEW_RESULT` signalerar uppdatering av resultattavla; `Event.data` är en [UserResult](../race-administrator/src/main/java/se/cag/labs/raceadmin/UserResult.java)

### Intern händelsbuss

#### POST /event
Detta är ett internt API (det exponeras alltså inte publikt) för en enkel händelsebuss som skickar händelseobjekt till alla uppkopplade [cag-rms-client-webpack](../cag-rms-client-webpack/README.md)-instanser.

Denna används f.n endast av [race-administrator](../race-administrator/README.md) för att vidarebefordra händelser som skall skickas till klienten. 

##### Begäran
Ett godtyckligt JSON-objekt som måste innehålla fältet _eventType_, t.ex

      {
        "eventType":"MyEvent",
        "someStuff": "XYZ"
      }
