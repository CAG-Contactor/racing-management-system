'use strict';
import * as ng1 from "angular";
import * as _ from "lodash";
export const appModule: ng1.IModule = ng1.module('app.module');
const htmlTemplate = require("./notification.tpl.html");

let messages = [];

appModule
  .directive('notificationTray', DirectiveFactory)
  .service('notificationService', ['$timeout', ServiceFactory]);

function ServiceFactory($timeout) {
  cleanMessages();
  return new Service($timeout);

  function cleanMessages() {
    let now = new Date().getTime();
    _.remove(messages, function (msg) {
      return now - msg.time > 5000;
    });
    $timeout(cleanMessages, 5000);
  }
}

function Service($timeout) {
  this.showErrorMessage = function (message) {
    addMessage('error', message);
  };
  this.showInfoMessage = function (message) {
    addMessage('info', message);
  };

  function addMessage(type, m) {
    // Async
    $timeout(function () {
      messages.unshift({type: type, msg: m, time: new Date().getTime()});
      if (messages.length > 4) {
        messages.pop();
      }
    }, 0);
  }
}

function DirectiveFactory() {
  return {
    scope: {},
    restrict: 'E',
    link: function (scope, elems, attrs) {
      scope.alerts = messages;
      scope.close = function (index) {
        messages.splice(index, 1);
      };
    },
    template: htmlTemplate
  };
}