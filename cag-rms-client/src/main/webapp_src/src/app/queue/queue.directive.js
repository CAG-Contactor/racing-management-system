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

  function Ctrl() {
    var vm = this;

    vm.enteredRacers = [ {name: 'Kalle Anka'}, {name: 'Arne Anka'}];
    vm.enterMe = enterMe;
    vm.removeMe = removeMe;
    vm.hasEntered = hasEntered;

    function enterMe() {
        // Get current logged in user
        var user = loggedInUser();
        vm.enteredRacers.push(user);
    }

    function removeMe() {
       var loggedIn = loggedInUser();
       vm.enteredRacers = _.filter(vm.enteredRacers, function(comp) {
          return comp.name !== loggedIn.name;
       });
    }

    function hasEntered() {
       var loggedIn = loggedInUser();
       var result = _.find(vm.enteredRacers, function(comp) {
          return comp.name === loggedIn.name;
       });

       return result !== undefined;
    }

    function loggedInUser() {
        return {name:'nyagubben@slasktratt.se'};
    }
  }

}());
