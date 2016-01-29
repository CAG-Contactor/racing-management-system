Current Race
============
Bygga och köra
--------------
TBD

Swagger Documentation
---------------------
Finns på <host>/swagger-ui.html

Specifikation
-------------
Denna tjänst hanterar aktuellt lopp. Gången är att man aktiverar ett lopp samt anger en URL för status-uppdateringar. När ett lopp är aktivt hanterar tjänsten anrop från tidmätningstjänsten. Dessutom övervakas att loppet inte tar för lång tid samt att det körs på rätt sätt. 
 
#### POST /startRace?callbackUrl=<string:URL of callback> 
Starta lopp.

Aktiverar loppet. From nu kommer tjänsten att lyssna efter /passageDetected-anrop och skicka callback-anrop till angiven callback-URL, med Status-objekt.

Dessutom sätts påbörjas övervakning av tidsgränsen att loppet inte tar för lång tid. 
Om startsensorn inte passeras inom XX minuter så avbryts loppet och callback anropas med 
event=TIME_OUT_NOT_STARTED och state=INACTIVE.
#### Query parameters
_callbackUrl_: URL som skall anropas för statusrapportering

#### POST /cancelRace
Avbryt pågående lopp.

Används för att manuellt avbryta aktivt lopp. 
När loppet avbrutits anropas callback med state=INACTIVE.

#### POST /passageDetected?sensorID=<string>&timestamp=<number>
Registrera passage av sensor.

Om lopp är aktivt så uppdateras aktuell status beroende på vilken sensor som passerats:

- Vid första passage av  startsensorn stämplas startTime och RaceEvent-uppdatering (event=START och state=ACTIVE) skickas till callback-URL; efterföljande passager av startsensorn ignoreras. Övervakning av tidsgräns för start avslutas och istället påbörjas övervakning av tidsgräns för målgång. Om startsensorn inte passeras inom XX minuter så avbryts loppet och callback anropas med event=TIME_OUT_NOT_FINISHED och state=INACTIVE.
- Vid passage av mellansensorn stämplas middleTime och RaceEvent skickas (event=SPLIT och state=ACTIVE); efterföljande passager av mellansensorn ignoreras 
- Om passage av slutsensorn registreras avslutas loppet, timeout-övervakning avbryts och RaceEvent skickas med state=INACTIVE och 
    - om mellansensorn passerats med event=FINISH, annars
    - om mellansensorn EJ passerats, med event=DISQUALIFIED.
#### Query parameters
_sensorID_: ID för passerad sensor {START,SPLIT,FINISH}
_timestamp_: LocalDateTime stämplad av detektor vid passage

#### GET /status
Aktuell status för lopp.

Används för att fråga efter aktuell status. Returnerar ett RaceStatus-objekt.
#### Response body

    {
      state:string (INACTIVE|ACTIVE)
      startTime: number (millis)
      splitTime: number (millis)
      finishTime: number (millis)
    }

#### Beskrivning

#### Dataobjekt
#####RaceEvent

    {
      event: string (NONE|START|SPLIT|FINISH|TIME_OUT_NOT_STARTED|TIME_OUT_NOT_FINISHED|DISQUALIFIED)
      startTime: number (millis)
      splitTime: number (millis)
      finishTime: number (millis)
      state: String (“ACTIVE”|”INACTIVE”)
    }
