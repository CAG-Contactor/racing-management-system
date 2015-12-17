Common
======
This module contains common utils to be used by all other modules.

Service: clientApi
------------------
This service provides access to _client-api_ in the back-end, thus acting 
as a proxy to the back-end.
 
### REST-methods
Each REST-method in _client-api_ has a corresponding method in this service.

Example:

Say that there exists a REST-method: 
    
    POST /stuff which accepts a JSON object in the body

then the service shall have a method:
 
    postStuff(anObject)

#### Common method for REST-access    
All the service methods for REST access are implemented using a common method 
so that we can implement common behaviour in one place. This method is implemented
using the Angular $http service.

    backendRequest(method, resourcePath, contents) 

_method_: the HTTP method
_resourcePath_: path to resource excluding server address
_contents_: an object:

    {
        body: <optional javascript object, which will be sent as JSON>,
        headers: { <optional set of headers>
            <name>:<value>
            :
        },
        params: { <optional set of query parameters>
            <name>:<value>
        }
    }
    
The method returns a _promise_ object.

Thus the _postStuff(anObject)_ would be implemented:

    backendRequest('POST', '/stuff', {body: anObject}) 
    
### Event channel
In addition the service makes it possible to add listeners to the web socket channel
used to send events __from__ _client-api_ __to__ this client.

