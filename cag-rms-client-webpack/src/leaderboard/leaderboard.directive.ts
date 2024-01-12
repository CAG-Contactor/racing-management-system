"use strict";
import * as ng1 from "angular";
import * as _ from "lodash";

export const appModule: ng1.IModule = ng1.module('app.module');
const htmlTemplate = require("./leaderboard.tpl.html");

appModule.directive('leaderboard', factory);

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
  vm.results = [];
  vm.displayValue = displayValue;

  $scope.$on('$destroy', function () {
    clientApiService.removeEventListener(handleEvent);
  });
  clientApiService.addEventListener(handleEvent);
  reload();

  function reload() {
    console.debug('Reload leaderboard');
    clientApiService.getResults()
      .then(function (response) {
        console.debug(response.data);
        vm.results = _.sortBy(response.data, 'time');
        console.debug('New leaderboard', vm.results);
      });
  }

  function handleEvent(event) {
    console.debug('Event: ', event);
    if (event.eventType === 'NEW_RESULT') {
      reload();
    }
  }

  function displayValue(tag) {
    switch (tag) {
      case 'FINISHED':
        return 'Godkänd';
      case 'WALKOVER':
        return 'Walkover';
      default:
        return 'Diskad';
    }
  }
}