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

  function Ctrl($uibModal, clientApiService) {
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
      var modalInstance = $uibModal.open({
        templateUrl: 'main/registerModal.tpl.html',
        controllerAs: 'vm',
        controller: function ($uibModalInstance) {
          var vm = this;
          vm.submit = submit;
          vm.cancel = cancel;
          vm.checkPassword = checkPassword;
          vm.password2 = undefined;
          vm.user = {
            userId: undefined,
            displayName: undefined,
            password: undefined
          };

          function checkPassword(userInfoForm) {
            if (userInfoForm.password.$dirty && userInfoForm.password2.$dirty) {
              userInfoForm.password.$setValidity('passwordMismatch', vm.password2 === vm.user.password);
              userInfoForm.password2.$setValidity('passwordMismatch', vm.password2 === vm.user.password);
              return userInfoForm.password.$valid && userInfoForm.password2.$valid;
            }
            return true;
          }

          function submit(userInfoForm) {
            vm.submitted = true;
            checkPassword(userInfoForm);
            if (userInfoForm.$valid) {
              $uibModalInstance.close(vm.user);
            } else {

            }
          }

          function cancel() {
            $uibModalInstance.dismiss('cancel');
          }
        },
        size: 'sm'
      });

      modalInstance.result
        .then(function (newUser) {
          console.debug('Save new user:', newUser);
        })
        .catch(function () {
          console.debug('Modal dismissed at: ' + new Date());
        });
    }

  }
}());
