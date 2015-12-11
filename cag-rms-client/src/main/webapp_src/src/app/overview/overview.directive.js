'use strict';
(function () {
  angular.module('cag-rms-client').directive('overview', factory);

  function factory() {
    return {
      restrict: 'E',
      templateUrl: 'overview/overview.tpl.html'
    };
  }
}());
