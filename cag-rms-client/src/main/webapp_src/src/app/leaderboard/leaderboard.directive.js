"use strict";
(function () {
  angular.module('cag-rms-client').directive('leaderboard', factory);

  function factory($log, $http) {
    $log.log("in leaderboard");

    $http({
      method: 'GET',
      url: 'http://localhost:10180/results'
    }).then(function successCallback(response) {
      console.debug(response.data);
    }, function errorCallback(response) {
    });

    return {
      restrict: 'E',
      templateUrl: 'leaderboard/leaderboard.tpl.html',
      controller: Ctrl,
      controllerAs: 'vm'
    };
  }

  function Ctrl() {
    var vm = this;
    vm.results = angular.fromJson('[{"id":"564f1bd9d4c64ebeaf277d6a","created":1448025049594,"user":{"id":null,"name":"kalle","email":"kalle@acme.com","password":null},"time":1111111,"middleTime":2222222,"result":"FINISHED"}]');
  }
}());