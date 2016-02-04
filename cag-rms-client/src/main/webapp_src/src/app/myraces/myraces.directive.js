"use strict";
(function () {
  angular.module('cag-rms-client').directive('myraces', factory);

  function factory() {
    return {
      restrict: 'E',
      templateUrl: 'myraces/myraces.tpl.html',
      controller: Ctrl,
      controllerAs: 'vm'
    };
  }

  function Ctrl($scope, clientApiService) {
    var vm = this;
    vm.results = [];
    vm.displayValue = displayValue;

    $scope.$on('$destroy', function(){clientApiService.removeEventListener(handleEvent);});
    clientApiService.addEventListener(handleEvent);
    reload();

    function reload() {
      console.debug('Reload my races');
      clientApiService.getMyRaces()
        .then(function (response) {
          console.debug(response.data);
          vm.myraces = _.sortBy(response.data,'time');
          console.debug('New my races',vm.myraces);
        });
    }

    function handleEvent(event) {
      console.debug('Event: ',event);
      if (event.eventType === 'NEW_RESULT') {
        reload();
      }
    }

    function displayValue(tag) {
      switch(tag) {
        case 'FINISHED': return 'Godkänd';
        case 'WALKOVER': return 'Walkover';
        default: return 'Diskad';
      }
    }
  }
}());