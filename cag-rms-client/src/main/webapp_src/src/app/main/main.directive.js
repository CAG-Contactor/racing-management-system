'use strict';
(function () {
  var USER_ID_KEY = 'cagrms.userid';

  angular.module('cag-rms-client').directive('cagMain', factory);

  function factory() {
    console.debug('Invoke cag-rm factory');
    return {
      restrict: 'E',
      templateUrl: 'main/main.tpl.html',
      controller: Ctrl,
      controllerAs: 'vm'
    };
  }

  function Ctrl(localStorageService) {
    var vm = this;
    vm.signIn = signIn;
    vm.signOut = signOut;
    vm.setSelection = setSelection;
    vm.userid = localStorageService.get(USER_ID_KEY);

    function signIn(userid, password) {
      console.debug('Sign in:', userid, password);
      vm.userid = userid;
      localStorageService.set(USER_ID_KEY, userid);
    }

    function signOut() {
      console.debug('Sign out');
      vm.userid = undefined;
      vm.selection = 'home';
      localStorageService.set(USER_ID_KEY, null);
    }

    function setSelection(selection) {
      console.debug('Set selection:',selection);
      vm.selection = selection;
    }
  }
}());
