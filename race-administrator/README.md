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

### POST /userqueue
#### Request body

    {
      userId: string (email)
    }

#### Beskrivning
Mottaget resultat sparas i databasen.
Lägg mottagen användare i kön. 
Om aktiv tävlande finns så händer inget mer.
Om lopp ej pågår så tas nästa användare från kön och sätts som aktiv tävlande. 
Lopp startas sedan genom att POST:a current-race/startRace med callbackUrl= <egen server>/onracestatusupdate.

### GET /userqueue
#### Response body

    [
      {
        "userId": string (email),
        "displayName": string
      },
      :
    ]
#### Beskrivning
Läsa upp aktuell kö. Det första elementet i kön är aktiv tävlande.

### POST /onracestatusupdate(status: RaceStatus)
#### Request body

    {
      event: string (NONE,START,SPLIT,FINISH,TIME_OUT_NOT_STARTED,TIME_OUT_NOT_FINISHED,DISQUALIFIED)
      startTime: number (millis)
      splitTime: number (millis)
      finishTime: number (millis)
      state: string (ACTIVE,INACTIVE)
    }
    
#### Beskrivning
Om det inte finns någon aktiv tävlande ignoreras request.

Om aktiv tävlande finns så anropas leaderloard/results med 

    {
      userId: string (email)
      time: number (millis)
      splitTime: number (millis)
      result: string (FINISH,WALKOVER,DISQUALIFIED)
    }

- result.time = status.finishTime - status.startTime
- result.middleTime = status.splitTime - status.startTime
- result.user = ID för aktiv tävlande
- result.result =
    FINISHED: status.event = FINISH
    WALKOVER: status.event = {TIME_OUT_NOT_STARTED ,TIME_OUT_NOT_FINISHED}
    DISQUALIFIED: status.event = alla andra händelsetyper

Dessutom skickas en händelse till klient via client-api/event:

    {
      userId: string (email)
      event: string (NONE,START,SPLIT,FINISH,TIME_OUT_NOT_STARTED,TIME_OUT_NOT_FINISHED,DISQUALIFIED)
      startTime: number (millis)
      splitTime: number (millis)
      finishTime: number (millis)
      state: string (ACTIVE,INACTIVE)
    }
