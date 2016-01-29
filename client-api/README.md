Client API
==========
API-fasad för cag-rms-client.

Swagger Documentation
---------------------
At <host>/swagger-ui.html

Beskrivning
-------------
Denna tjänst tillhandahåller en API-fasad gentemot övriga tjänster i back-end.

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

### Eventbuss

##### POST /event
Skicka event till alla uppkopplade cag-rms-client-instanser.
##### Request body
Ett godtyckligt JSON-objekt som måste innehålla fältet _eventType_, t.ex

      {
        "eventType":"MyEvent",
        "someData": "XYZ"
      }
