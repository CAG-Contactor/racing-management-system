import 'bootstrap';
import 'bootstrap/css/bootstrap.css!';
import angular from 'angular';
import appModule from './app/AppModule';

// Use manual boostrapping of angular to make sure load order is correct.
console.debug('AppModule:', appModule.name);
angular.bootstrap(document, [appModule.name]);

console.debug('main has started');