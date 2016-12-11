User interface for CAG Racing Management System
================================================

CAG Racing Management System
============================


Build
-----
_This section needs to be rewritten; also, the build system must be modified to support it._

    $ mvn clean install [ -Dclientapi.base=<clientapi server address> ] [-Dbuildinfo=<buildinfo>][ -Pclean-node ]

- clientapi server address: see below
- clean-node: this profile removes node/ node_modules and /vendor src/main/webapp_src
- buildinfo: some string identifying the build, e.g "$(git log -1 --pretty='%H %cd')"; 
  defaults to ${maven.build.timestamp}. This build info is displayed when clicking on 
  "CAG DROID RACE"-logo in upper left corner.

 
This installs node, npm and builds the entire web application.

The unminified result is then available in src/main/webapp_build and
the minified result is available in src/main/webapp.

Develop
-------
### Code structure
The application is available in `src`
- `src/index.html`: main page
- `src/assets`: images, fonts

#### Application structure
The following application modules exists

- `src/common`: common stuff in the application
    - `clientApi.service`: service for accessing back-end
- `src/main`: component that implements the main page contents with navigation bar and content area containing the other components.  
- `src/overview`: component that implements the overview view (`<overview></overview>`)
- `src/queue`: component that implements the queue view (`<cag-queue></cag-queue>`)
- `src/leaderboard`: component that implements the leaderboard view (`<leaderboard></leaderboard>`)
- `src/currentrace`: component that implements the currentrace view (`<current-race data-user="<user name>"></current-race>`)
- `src/notification`: service for showing error and info notifications

### Run application in development server

    $ npm install && npm start

Debugging Events
================
When debugging it is possible to show notifications for all events received via
the websocket channels.

This is done by setting the parameter ls.droid.showevents to true in the
local storage of the browser (in chrome it is accessed in developer tools -> resources).
