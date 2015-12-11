'use strict';
(function () {

  angular.module('cag-rms-client').directive('currentRace', factory);

  function factory() {
    return {
      restrict: 'E',
      templateUrl: 'currentrace/currentrace.tpl.html'
    };
  }

}());
