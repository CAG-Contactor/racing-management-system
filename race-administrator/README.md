Race Administrator
==================
Bygga och köra
--------------
TBD

Swagger Documentation
---------------------
Finns på <host>/swagger-ui.html

Specifikation
-------------
Denna tjänst hanterar en FIFO-kö med tävlande och orkestrerar lopp.

### REST-metoder
#### POST /userqueue
##### Request body
se.cag.labs.raceadmin.User

##### Beskrivning
Mottaget resultat sparas i databasen.
Lägg mottagen användare i kön. 
Om aktiv tävlande finns så händer inget mer.
Om lopp ej pågår så tas nästa användare från kön och sätts som aktiv tävlande. 
Lopp startas sedan genom att POST:a current-race/startRace med callbackUrl= <egen server>/on-race-status-update.

#### GET /userqueue
##### Response body
List<se.cag.labs.raceadmin.User>

##### Beskrivning
Läsa upp aktuell kö.

#### GET /currentrace
##### Response body
se.cag.labs.raceadmin.RaceStatus

##### Beskrivning
Läsa upp status för pågående lopp.


#### POST /on-race-status-update
##### Request body
se.cag.labs.raceadmin.RaceStatus
    
##### Beskrivning
Om det inte finns någon aktiv tävlande ignoreras request.

Om aktiv tävlande finns och loppet fortfarande pågår skickas event:

    {
        "eventType":"CURRENT_RACE_STATUS", 
        "data": <se.cag.labs.raceadmin.RaceStatus>
    }

till eventbuss.

Annars, om aktiv tävlande finns och loppet är avslutat, så anropas leaderloard/results med se.cag.labs.raceadmin.UserResult.

- result.time = status.finishTime - status.startTime
- result.splitTime = status.splitTime - status.startTime
- result.user = se.cag.labs.raceadmin.User för aktiv tävlande
- result.result =
    FINISHED: om status.event == FINISH
    WALKOVER: om status.event == {TIME_OUT_NOT_STARTED ,TIME_OUT_NOT_FINISHED}
    DISQUALIFIED: om status.event == alla andra händelsetyper

Dessutom skickas ett event: 

    {
        "eventType":"NEW_RESULT",
        "data":<se.cag.labs.raceadmin.UserResult>
    }
    
till eventbuss.

#### POST /reset-race

##### Beskrivning
Avbryt pågående lopp. Anropar <current-race>/cancelRace.
