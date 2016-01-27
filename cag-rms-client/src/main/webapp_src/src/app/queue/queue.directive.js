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

    vm.enteredRacers = [];
    vm.enterMe = enterMe;
    vm.removeMe = removeMe;
    vm.hasEntered = hasEntered;

    clientApiService.addEventListener(handleEvent);

    $scope.$on(
      '$destroy',
      function () {
        console.debug('destroy called');
        clientApiService.removeEventListener(handleEvent);
      }
    );

    loadUserQueue();


    function handleEvent(event) {
      console.debug('Event: ', event);
      if (event.eventType === 'QUEUE_UPDATED') {
        loadUserQueue();
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
