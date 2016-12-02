import * as ng1 from "angular";

export const appModule = ng1
  .module('app.module', [
    'ui.bootstrap',
    'angular-md5',
    'LocalStorageModule',
    'ngResource',
    'ngAnimate'
  ])
  .constant('APP_CONFIG', {
    clientApi: 'http://localhost:10580' //Replaced by grunt copy
  });

import "./main";

console.debug('appModule is initialized:', appModule);
