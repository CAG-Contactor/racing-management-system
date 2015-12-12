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

  function Ctrl(clientApiService) {
    var vm = this;
    var connectionStyle = {
      color:'red'
    };
    clientApiService.setConnectionListener(connectionListener);
    vm.signIn = signIn;
    vm.signOut = signOut;
    vm.setSelection = setSelection;
    vm.connectionStyle = connectionStyle;
    vm.connected = false;
    vm.currentUser = clientApiService.getCurrentUser();

    function connectionListener(state) {
        if (state === 'CONNECTED') {
          vm.connected = true;
        } else {
          vm.connected = false;
        }
    }

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
