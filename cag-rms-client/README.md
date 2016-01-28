CAG Racing Management System
============================

Build
-----

    $ mvn clean install [ -Dclientapi.base=<clientapi server address> ] [ -Pclean-node ]

- clientapi server address: see below
- clean-node: this profile removes node/ node_modules and /vendor src/main/webapp_src

This installs node, npm and builds the entire web application.

The unminified result is then available in src/main/webapp_build and
the minified result is available in src/main/webapp.

Develop
-------
### Code structure
The application is available in src/main/webapp_src

- src/index.html: main page
- src/app: the actual application code (js, html)
- src/assets: images, fonts
- src/less: as the name implies... base.less is the application specific styles
- src/common: some basic stuff, not part of the application

#### Application structure
The following application modules exists

- src/app/common: common stuff in the Application
  - clientApi.service: Service for accessing back-end
- src/app/main: component that implements the main page contents with navigation
bar and content area containing the other components.  
- src/app/overview: component that implements the overview view (<overview></overview>)
- src/app/queue: component that implements the queue view (<cag-queue></cag-queue>)
- src/app/leaderboard: component that implements the leaderboard view (<leaderboard></leaderboard>)
- src/app/currentrace: component that implements the currentrace view (<current-race data-user="<user name>"></current-race>)
- src/app/notification: service for showing error and info notifications

### Run application
This requires that maven build has been done as described above.

  $ cd src/main/webapp_src
  $ ./node/npm install
  $ ./node_modules/.bin/grunt serve --clientapibase=<clientapi server address>

Open browser at http://localhost:9002  

- clientapi server address: the base URL of the client API backend. Defaults to localhost:10580


Debugging Events
================
When debugging it is possible to show notifications for all events received via
the websocket channels.

This is done by setting the parameter ls.droid.showevents to true in the
local storage of the browser (in chrome it is accessed in developer tools -> resources).
