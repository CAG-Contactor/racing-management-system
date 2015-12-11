'use strict';
(function () {
  angular.module('cag-rms-client').directive('overviewMessage', factory);

  function factory() {
    return {
      scope: true,
      restrict: 'E',
      templateUrl: 'overview/overview.message.tpl.html',
      require: '^overview',
      replace: 'true',
      link: function(scope, elem, attrs) {
      }
    };
  }
}());
