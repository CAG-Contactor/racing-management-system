Client API
==========
API-fasad som agerar front mot övriga tjänster. 
Denna fasad används av [cag-rms-client-webpack](../cag-rms-client-webpack/README.md).

Swagger Documentation
---------------------
Finns på <host>/swagger-ui.html

Beskrivning
-------------
Denna tjänst tillhandahåller en API-fasad gentemot övriga tjänster i back-end.

API:et består av ett REST-gränssnitt samt en websocket-ändpunkt via vilken händelser kan tas emot.

Dessutom tillhandahåller den tjänsten att skicka (broadcast) händelse-meddelanden
till alla cag-rms-client-instanser som är uppkopplade.

REST-metoder
-----------------------
#### ClientAPI

##### POST /users
Registrera en användare genom att vidarebefordra till _<user-manager>/users_
##### Request body
A se.cag.labs.cagrms.clientapi.service.User

##### POST /login
Logga in användare genom att vidarebefordra till _<user-manager>/login_
##### Request body
A se.cag.labs.cagrms.clientapi.service.User

##### POST /logout
Logga ut användare genom att vidarebefordra till _<user-manager>/logout?token=<värde från X-AuthToken header>_
##### Header parameter
X-AuthToken: sessionstoken för inloggad användare

##### GET /leaderboard
Hämta resultattavla genom att vidarebefordra till _<leaderboard>/results_
##### Response body
List<se.cag.labs.cagrms.clientapi.service.UserResult>

##### GET /userqueue
Hämta kön genom att vidarebefordra till (GET) _<race-administrator>/userqueue
##### Response body
List<se.cag.labs.cagrms.clientapi.service.User>

#### POST /userqueue
Anmäl genom att vidarebefordra till (POST) _<race-administrator>/userqueue_
##### Request body
A se.cag.labs.cagrms.clientapi.service.User

##### DELETE /userqueue
Avanmäl genom att vidarebefordra till (DELETE) _<race-administrator>/userqueue_
##### Request body
A se.cag.labs.cagrms.clientapi.service.User

##### GET /currentrace
Vidarebefordra till (GET) _<race-administrator>/currentrace
##### Response body
A se.cag.labs.cagrms.clientapi.service.RaceStatus

#### GET /lastrace
Läsa upp status för senast avslutade lopp.
##### Response body
se.cag.labs.raceadmin.RaceStatus

##### POST /reset-race
Avbryt genom att vidarebefordra till _<race-administrator>/reset-race

### Extern ändpunkt för att lyssna på händelser
Denna är implementerad med en websocket-ändpunkt med URI:n: `ws://<host>/eventchannel`

Händelser som tas emot är JSON-objekt motsvarande java-klassen [Event](../race-administrator/src/main/java/se/cag/labs/raceadmin/peerservices/Event.java).
 
Fälted `Event.data` tolkas olika beroende på `Event.eventType`. Följande händelser kan komma via denna kanal:

- `Event.eventType=CURRENT_RACE_STATUS` signalerar uppdatering av status för lopp; `Event.data` är en [RaceStatus](../race-administrator/src/main/java/se/cag/labs/raceadmin/RaceStatus.java)
- `Event.eventType=QUEUE_UPDATED` signalerar uppdatering av kön med anmälda tävlande; `Event.data` är en [User](../race-administrator/src/main/java/se/cag/labs/raceadmin/User.java)
- `Event.eventType=NEW_RESULT` signalerar uppdatering av resultattavla; `Event.data` är en [UserResult](../race-administrator/src/main/java/se/cag/labs/raceadmin/UserResult.java)

### Intern händelsbuss

##### POST /event
Detta är ett internt API (det exponeras alltså inte publikt) för en enkel händelsebuss som skickar händelseobjekt till alla uppkopplade [cag-rms-client-webpack](../cag-rms-client-webpack/README.md)-instanser.

Denna används f.n endast av [race-administrator](../race-administrator/README.md) för att vidarebefordra händelser som skall skickas till klienten. 

##### Request body
Ett godtyckligt JSON-objekt som måste innehålla fältet _eventType_, t.ex

      {
        "eventType":"MyEvent",
        "someStuff": "XYZ"
      }
