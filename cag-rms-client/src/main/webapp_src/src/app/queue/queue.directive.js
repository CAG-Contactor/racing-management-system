'use strict';
(function () {
  var USER_ID_KEY = 'cagrms.userid';

  angular.module('cag-rms-client').directive('cagQueue', factory);

  function factory() {
    return {
      restrict: 'E',
      templateUrl: 'queue/queue.tpl.html',
      controller: Ctrl,
      controllerAs: 'vm'
    };
  }

  function Ctrl(clientApiService) {
    var vm = this;

    vm.enteredRacers = [];
    vm.enterMe = enterMe;
    vm.removeMe = removeMe;
    vm.hasEntered = hasEntered;

    loadUserQueue();

    function loadUserQueue() {
      return clientApiService.getUserQueue()
        .then(function (response) {
          vm.enteredRacers = response.data;
        });
    }

    function enterMe() {
      clientApiService.registerForRace()
        .then(loadUserQueue);
    }

    function removeMe() {
      clientApiService.unregisterFromRace()
        .then(loadUserQueue);
    }

    function hasEntered() {
      var loggedIn = clientApiService.getCurrentUser();
      var result = _.find(vm.enteredRacers, function (comp) {
        return comp.name === loggedIn.name;
      });

      return result !== undefined;
    }
  }

}());
