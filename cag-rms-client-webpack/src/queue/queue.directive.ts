import * as ng1 from "angular";
import * as _ from "lodash";
export const appModule: ng1.IModule = ng1.module('app.module');
const htmlTemplate = require("./queue.tpl.html");

appModule.directive('cagQueue', factory);

function factory() {
  return {
    restrict: 'E',
    template: htmlTemplate,
    controller: ['$scope', 'clientApiService', Ctrl],
    controllerAs: 'vm'
  };
}

function Ctrl($scope, clientApiService) {
  let vm = this;
  let racingUser;
  let loggedInUser = clientApiService.getCurrentUser();

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
    .then(function (response) {
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
    let loggedIn = clientApiService.getCurrentUser();
    let result = _.find(vm.enteredRacers, function (comp:any) {
      return comp.userId === loggedIn.userId;
    });

    return result !== undefined;
  }
}
