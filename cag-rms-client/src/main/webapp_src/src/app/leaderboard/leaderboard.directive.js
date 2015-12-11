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

  function Ctrl(localStorageService) {
    var vm = this;
    vm.results = results;

    function results() {
      console.debug('Results:');
      return 'Kalle';
    }
  }
}());