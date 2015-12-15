Leaderboard
===========
Bygga och starta
----------------
TBD

Swagger Documentation
---------------------
Finns på <host>/swagger-ui.html

Specifikation
-------------
Denna tjänst hanterar resultat från körda lopp. 
### Registrera nytt resultat
#### Resurs
POST /results
#### Request body
_UserResult_
    

####Beskrivning
Mottaget resultat sparas i databasen.

### Hämta resultatlista
#### Resurs
GET /results 
#### Response body

    [ UserResult, ... ]

####Beskrivning
Returnera alla sparade resultat sorterad på tid. Lopp som ej har result=FINISHED läggs sist i listan, sorterad på användarnamn.

#### Dataobjekt
##### UserResult
    {
      userId: String (email),
      time: number (millis)
      splitTime: number (millis)
      result: string (FINISHED,WALKOVER,DISQUALIFIED)
    }