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

#### POST /results
Registrera nytt resultat.

Mottaget resultat sparas i databasen.
#### Request body
se.cag.labs.leaderboard.UserResult

#### GET /results 
Hämta resultatlista.

Returnera alla sparade resultat sorterad på tid. Lopp som ej har result=FINISHED läggs sist i listan, sorterad på användarnamn.
#### Response body
List<se.cag.labs.leaderboard.UserResult>

