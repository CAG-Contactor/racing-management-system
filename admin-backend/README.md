Admin-backend
=============
This is the back-end of the admin.
 
REST-API
--------

### GET /registered-races
Gets all registered (completed) races.

#### Response
##### Media type: application/json
An array JSON-objects corresponding to [UserResult](../leaderboard/src/main/java/se/cag/labs/leaderboard/UserResult.java) 

```json
[{
  id:string,
  userId:string, // email
  time:number,
  splitTime:number,
  result: "FINISHED" | "WALKOVER" | "DISQUALIFIED" 
}]
```
##### Media type: text/csv
A CSV-file with columns:

```csv
id;userId;time;splitTime;result
```

### DELETE /registered-races/{race-id}
Deletes the race corresponding to teh specified ID which is an ID as obtained from the GET-method.

### POST /cancel-active-race
Cancel the active race.

### GET /service-status
Gets the status of the back-end services and the Raspberry.

#### Response
A JSON object:

```json
{
  "service-name-1":{
    alive: boolean,
    dbUp: boolean,
    info: string[]
  },
  :
  "service-name-n":{
    ...
  }
}
```

### GET /users
Gets the registered users.

#### Response
##### Media type: application/json
An array of JSON objects:

```json
[{
  id:string,
  userId: string // email
  displayName: string
}]
```

##### Media type: text/csv
A CSV-file with columns:

```csv
userId;displayName
```


