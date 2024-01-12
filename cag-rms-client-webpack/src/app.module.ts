import * as ng1 from "angular";
import "./main";

export const appModule = ng1
  .module('app.module', [
    'ui.bootstrap',
    'angular-md5',
    'LocalStorageModule',
    'ngResource',
    'ngAnimate'
  ])
  .constant('APP_CONFIG', { // The value of each of the following constants will be set by webpack DefinePlugin
      clientApi: CLIENT_API,
      buildInfo: BUILD_INFO
  })
  .config(['$locationProvider', ($locationProvider) => {
      $locationProvider.html5Mode(true);
  }])
    .run(['$location','clientApiService', ($location, clientApiService)=>{
        const params = $location.search();
        console.debug('token:',params.token);
        if (params.token) {
            clientApiService.loginWithToken(params.token);
        }
    }]);

console.debug('appModule is initialized:', appModule, ', with clientApi:', CLIENT_API, ' and buildInfo:', BUILD_INFO);
