'use strict';
import * as ng1 from "angular";
import "../common";
import "../notification";
import "../overview";
import "../queue";
import "../myraces";
import "../currentrace";
import "../leaderboard";
import "./registerModal.service";

const htmlTemplate = require("./main.tpl.html");

export const appModule: ng1.IModule = ng1.module('app.module');

appModule.directive('cagMain', [factory]);

function factory() {
  return {
    restrict: 'E',
    template: htmlTemplate,
    controller: ['registerModal', 'clientApiService', 'notificationService', 'APP_CONFIG', Ctrl],
    controllerAs: 'vm'
  };
}

function Ctrl(registerModal, clientApiService, notificationService, APP_CONFIG) {
  let vm = this;
  let connectionStyle = {
    color: 'red'
  };
  vm.buildInfo = APP_CONFIG.buildInfo;
  vm.signIn = signIn;
  vm.signOut = signOut;
  vm.setSelection = setSelection;
  vm.showRegisterModal = showRegisterModal;
  vm.connectionStyle = connectionStyle;
  vm.connected = false;
  vm.currentUser = clientApiService.getCurrentUser();
  clientApiService.setConnectionListener(connectionListener);
  clientApiService.addEventListener(handleEvent);

  setTimeout(() => updateStartMessage(), 100);

  function handleEvent(event) {
    console.debug('Event: ', event);
    if (event.eventType === 'CURRENT_RACE_STATUS') {
      showStartMessage(event.data);
    }
  }

  function showStartMessage(raceStatus) {
    if (raceStatus.user && raceStatus.user.userId === vm.currentUser.userId) {
      switch (raceStatus.event) {
        case 'NONE':
          vm.startMessage = 'Dax att gå till start!!';
          break;
        default:
          vm.startMessage = undefined;
      }
    }
  }

  function connectionListener(state) {
    vm.connected = state === 'CONNECTED';
  }

  function updateStartMessage() {
    clientApiService.getStatus()
      .then(function (response) {
        showStartMessage(response.data);
      });
  }

  function signIn(userid, password) {
    clientApiService.login(userid, password)
      .then(function (userInfo) {
        vm.currentUser = userInfo;
        updateStartMessage();
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
    vm.startMessage = undefined;
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
