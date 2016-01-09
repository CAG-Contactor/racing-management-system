Bygga och köra
Bygga och köra
--------------
TBD

Swagger Documentation
---------------------
Finns på <host>/swagger-ui.html

Specifikation
-------------
Denna tjänst hanterar registrerade användare samt pågående användarsessioner.

### Logga in användare
#### Resurs
POST /login

#### Request body

    {
        userId:string (email)
        password:string
    }

#### Beskrivning
Om tjänsten redan har en session för denna användare, samt sessionen ej har löpt ut, svarar tjänsten 200 OK + token för denna session.
Om tjänsten ej har en session, eller om sessionen har löpt ut för denna användare och lösenordet är korrekt skapas ett nytt _Session_-objekt och sparas. Sedan svarar tjänsten med 200 OK + token för session.
Om lösenordet inte är korrekt svarar tjänsten med 401 Unauthorized.

### Logga ut användare
#### Resurs
POST /logout?token=<string>

#### Query parameters
_token_: godtycklig sträng som identifierar session

#### Beskrivning
Om tjänsten har en session för angivet _token_, radera sessionen. I annat fall, gör ingenting.

### Hämta användare för token (=session)
#### Resurs
GET /users?token=<string>

#### Query parameters
_token_: godtycklig sträng som identifierar session

#### Response body
_UserInfo_

#### Beskrivning
Om det finns en session för angivet token, samt att sessionen ej har löpt ut, svarar tjänsten med 200 OK. 
Om 

- token saknas, eller 
- om det finns en session men den har löpt ut, eller 
- om det inte finns någon användare registrerad för angivet token, 

så svarar tjänsten med 404 Not found.

### Hämta användarinfo för angivet användar-ID
#### Resurs
GET /users?userId=<string>

#### Query parameters
userId: loginnamnet (email) för användaren

#### Response body
_UserInfo_

#### Beskrivning
Tjänsten svarar med 200 OK för den användare som har detta ID.

Om användare för detta ID saknas svara tjänsten med med 404 Not Found.

### Registrera ny användare
#### Resurs
POST /users

#### Request body

    {
      userId:string (email),
      displayName:string
      password:string
    }

#### Beskrivning
Om användarnamnet är ledigt sparas ett nytt User-objekt i databasen och tjänsten svarar med 201 Created.
Om användarnamnet är upptaget svarar tjänsten med 400 Bad Request.

#### Dataobjekt
##### UserInfo

    {
      userId: String (email),
      displayName: String
    }

##### User (DB)

    {
        id: ObjectID,
        userId: String (email),
        displayName: String,
        password: String (krypterad),
    }

##### Session

    {
      token: String,
      userId: ObjectID,
      timeout: LocalDateTime,
    }