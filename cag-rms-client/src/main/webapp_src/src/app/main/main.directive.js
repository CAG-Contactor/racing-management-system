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

  function Ctrl(registerModal, clientApiService, notificationService) {
    var vm = this;
    var connectionStyle = {
      color: 'red'
    };
    vm.signIn = signIn;
    vm.signOut = signOut;
    vm.setSelection = setSelection;
    vm.showRegisterModal = showRegisterModal;
    vm.connectionStyle = connectionStyle;
    vm.connected = false;
    vm.currentUser = clientApiService.getCurrentUser();
    clientApiService.setConnectionListener(connectionListener);

    function connectionListener(state) {
      vm.connected = state === 'CONNECTED';
    }

    function signIn(userid, password) {
      clientApiService.login(userid, password)
        .then(function (userInfo) {
          vm.currentUser = userInfo;
        })
        .catch(function (error) {
          console.log(error);
          notificationService.showErrorMessage('Var det verkligen rätt inloggningsuppgifter!?');
        });
    }

    function signOut() {
      console.debug('Sign out');
      vm.currentUser = undefined;
      vm.selection = 'home';
      clientApiService.logout();
    }

    function setSelection(selection) {
      console.debug('Set selection:', selection);
      vm.selection = selection;
    }

    function showRegisterModal() {
      registerModal.show()
        .then(function (newUser) {
          console.debug('Save new user:', newUser);
          clientApiService.addUser(newUser)
            .then(function () {
              notificationService.showInfoMessage('Fixat, nu är du reggad!');
            })
            .catch(function () {
              notificationService.showErrorMessage('Nä, det det gick inge bra att regga det användarnamnet, det är nog upptaget.');
            });
        })
        .catch(function () {
          console.debug('Cancel adding of user');
        });
    }
  }
}());
