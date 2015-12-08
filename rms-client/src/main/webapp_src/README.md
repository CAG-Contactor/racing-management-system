## Quick start
1 - Install Node.js
2 - Install Git client (if not already installed)
3 - Use a connection where the proxy does not bother you to connect to, for instance, npmjs.org or github.com
4 - From this directory run the following commands:

```
* Note that the following commands must be executed using your mobile phone's network connection. The folksam proxy
* does not allow git protocol communication.
npm install
node node_modules/bower/bin/bower install (creates a directory named vendor and downloads all third-party modules specified in bower.json to it)
node node_modules/grunt-cli/bin/grunt build (builds the webapp using Gruntfile.js and build_config.js)
```
Include this project (the pom.xml in the i84ds03-webapp-template directory) in your maven root pom.xml.
Then run `mvn clean install` from the root.

## Watch files with Grunt

If you're actively developing the front end you can watch files for changes and run the app uncompressed.

Run `grunt serve` from this directory and open up `http://localhost:9000`

If you need to use another port, you can change it in Gruntfile.js in this directory. Open it and search for proxies: