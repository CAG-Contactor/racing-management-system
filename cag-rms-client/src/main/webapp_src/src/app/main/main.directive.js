'use strict';
(function () {

  angular.module('cag-rms-client').directive('cagMain', factory);

  function factory() {
    return {
      restrict: 'E',
      templateUrl: 'main/main.tpl.html',
      controller: Ctrl,
      controllerAs: 'vm'
    };
  }

  function Ctrl(localStorageService, clientApiService) {
    var vm = this;
    vm.signIn = signIn;
    vm.signOut = signOut;
    vm.setSelection = setSelection;
    vm.currentUser = clientApiService.getCurrentUser();

    function signIn(userid, password) {
      clientApiService.login(userid,password)
      .then(function(userInfo){
        vm.currentUser = userInfo;
      });
    }

    function signOut() {
      console.debug('Sign out');
      vm.currentUser = undefined;
      vm.selection = 'home';
      clientApiService.logout();
    }

    function setSelection(selection) {
      console.debug('Set selection:',selection);
      vm.selection = selection;
    }
  }
}());
