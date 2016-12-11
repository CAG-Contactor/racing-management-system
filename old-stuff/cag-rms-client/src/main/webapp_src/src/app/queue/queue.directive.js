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

  function Ctrl($scope, clientApiService) {
    var vm = this;
    var racingUser;
    var loggedInUser = clientApiService.getCurrentUser();

    vm.enteredRacers = [];
    vm.enterMe = enterMe;
    vm.removeMe = removeMe;
    vm.hasEntered = hasEntered;
    vm.isActiveRace = isActiveRace;

    clientApiService.addEventListener(handleEvent);

    $scope.$on(
      '$destroy',
      function () {
        console.debug('destroy called');
        clientApiService.removeEventListener(handleEvent);
      }
    );

    clientApiService.getStatus()
      .then(function(response) {
        handleStatus(response.data);
      });
    loadUserQueue();

    function isActiveRace() {
      return racingUser && racingUser.userId === loggedInUser.userId;
    }

    function handleStatus(raceStatus) {
      if (raceStatus.state === 'ACTIVE') {
        racingUser = raceStatus.user;
      } else {
        racingUser = null;
      }
    }

    function handleEvent(event) {
      console.debug('Event: ', event);
      switch (event.eventType) {
        case  'QUEUE_UPDATED':
          loadUserQueue();
          return;
        case 'CURRENT_RACE_STATUS':
          handleStatus(event.data);
          return;
      }
    }

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
        return comp.userId === loggedIn.userId;
      });

      return result !== undefined;
    }
  }

}());
