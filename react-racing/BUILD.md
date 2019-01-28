# Build react-racing with maven
The application can be built using maven and a NodeJS environment installed locally by maven.

## Build for production
Build the production application.

```bash
$ mvn clean package [-Dclientapi.base=<base-URL for client API>] [-Pclean-node]
```
Parameters
* clientapi.base: URL of the client-API backend server; default: http://localhost:10580

Profiles
* clean-node: removes _./node_modules_ and _./node_ when doing _clean_

The resulting application files are available in _./build_. Just copy these files to your web server.
