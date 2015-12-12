(function () {
  "use strict";
  angular.module('cag-rms-client')
    .service('clientApiService', function ($resource, $q, $http, APP_CONFIG, localStorageService) {
      var eventBus = new EventBus(APP_CONFIG);
      var login = $resource('/login');
      var service = new Service($resource, $q, $http, APP_CONFIG, localStorageService);
      return service;
    });

  function Service($resource, $q, $http, APP_CONFIG, localStorageService) {
    var USER_INFO_KEY = 'cagrms.userinfo';
    var TOKEN_KEY = 'cagrms.token';

    this.login = function (userId, password) {
      console.debug('Logging in:', userId);
      return $q.when({displayName: 'Kalle Banan', email: 'kallebanan@bageriet.se'})
        .then(function(userInfo) {
          localStorageService.set(USER_INFO_KEY, userInfo);
          localStorageService.set(TOKEN_KEY, 'abc-123');
          return userInfo;
        });
    };
    this.logout = function () {
      console.debug('Logging out');
      localStorageService.set(USER_INFO_KEY, null);
      localStorageService.set(TOKEN_KEY, null);
      return $q.when({});
    };
    this.getCurrentUser = function() {
      return localStorageService.get(USER_INFO_KEY);
    };
    this.getResults = function () {
      //$http({
      //  method: 'GET',
      //  url: 'http://localhost:10180/results'
      //})
      return $q.when(['dummy1', 'dummy2']);
    };
  }

  function EventBus(APP_CONFIG) {
    var eventBusWS = new SockJS('http://' + APP_CONFIG.clientApi + '/eventbus');
    eventBusWS.onopen = function () {
      console.debug('Info: WebSocket connection opened.');
    };
    eventBusWS.onmessage = function (event) {
      console.debug('Received: ', JSON.parse(event.data));
    };
    eventBusWS.onclose = function () {
      console.debug('Info: WebSocket connection closed.');
    };
  }
}());