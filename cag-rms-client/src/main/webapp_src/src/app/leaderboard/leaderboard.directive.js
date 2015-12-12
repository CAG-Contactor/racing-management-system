"use strict";
(function () {
  angular.module('cag-rms-client').directive('leaderboard', factory);

  function factory($log, clientApiService) {
    $log.log("in leaderboard");

    clientApiService.getResults()
      .then(function successCallback(response) {
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