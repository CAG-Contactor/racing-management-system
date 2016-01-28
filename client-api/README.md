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

#### Registrera användare
##### Resurs
POST /users

##### Beskrivning
Registrera en användare genom att vidarebefordra till _<user-manager>/users_

##### Request body
A se.cag.labs.cagrms.clientapi.service.User

#### Logga in
##### Resurs
POST /login

##### Beskrivning
Logga in användare genom att vidarebefordra till _<user-manager>/login_

##### Request body
A se.cag.labs.cagrms.clientapi.service.User

#### Logga ut
##### Resurs
POST /logout

##### Beskrivning
Logga ut användare genom att vidarebefordra till _<user-manager>/logout?token=<värde från X-AuthToken header>_

##### Header parameter
X-AuthToken: sessionstoken för inloggad användare

#### Läsa upp resultattavla
##### Resurs
GET /leaderboard

##### Beskrivning
Hämta resultattavla genom att vidarebefordra till _<leaderboard>/results_

##### Response body
List<se.cag.labs.cagrms.clientapi.service.UserResult>

#### Läsa upp kön av användare som väntar på att köra lopp
##### Resurs
GET /userqueue

##### Beskrivning
Hämta kön genom att vidarebefordra till (GET) _<race-administrator>/userqueue

##### Response body
List<se.cag.labs.cagrms.clientapi.service.User>

#### Anmäla sig till lopp
##### Resurs
POST /userqueue

##### Beskrivning
Anmäl genom att vidarebefordra till (POST) _<race-administrator>/userqueue_

##### Request body
A se.cag.labs.cagrms.clientapi.service.User

#### Avanmäla sig från lopp
##### Resurs
DELETE /userqueue

##### Beskrivning
Avanmäl genom att vidarebefordra till (DELETE) _<race-administrator>/userqueue_

##### Request body
A se.cag.labs.cagrms.clientapi.service.User

#### Hämta information om aktuellt lopp
##### Resurs
GET /currentrace

##### Beskrivning
Avanmäl genom att vidarebefordra till (GET) _<race-administrator>/currentrace

##### Request body
A se.cag.labs.cagrms.clientapi.service.User

#### Avbryt pågående lopp
##### Resurs
POST /reset-race

##### Beskrivning
Avbryt genom att vidarebefordra till _<race-administrator>/reset-race

### Eventbuss

#### Skicka event
##### Resurs
POST /event

##### Beskrivning
Skicka event till alla uppkopplade cag-rms-client-instanser.

##### Request body
Ett godtyckligt JSON-objekt som måste innehålla fältet _eventType_, t.ex

      {
        "eventType":"MyEvent",
        "someData": "XYZ"
      }
