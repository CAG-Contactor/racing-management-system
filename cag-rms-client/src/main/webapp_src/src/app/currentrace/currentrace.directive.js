'use strict';
(function () {
  angular.module('cag-rms-client').directive('currentRace', ['$timeout', '$http', function ($timeout, clientApiService) {
    return {
      scope: {},
      restrict: 'E',
      templateUrl: 'currentrace/currentrace.tpl.html',
      controller: Ctrl
    };
    function Ctrl($scope, clientApiService) {
      var timerHandle, statusTimerHandle;
      var tzOffset = new Date(0).getTimezoneOffset() * 1000 * 60;
      var scope = $scope;
      scope.isRaceActive = false;
      scope.runningTime = 0;
      scope.startTime = undefined;
      scope.splitTime = undefined;
      scope.finishTime = undefined;

      timer();

      clientApiService.addEventListener(handleEvent);

      scope.$on(
        '$destroy',
        function () {
          console.debug('destroy called');
          clientApiService.removeEventListener(handleEvent);
          $timeout.cancel(timerHandle);
          $timeout.cancel(statusTimerHandle);
        }
      );

      getStatus();
      updateLastRace();

      // --- Local functions ---

      function handleEvent(event) {
        console.debug('Event: ', event);
        if (event.eventType === 'CURRENT_RACE_STATUS') {
          console.debug('New race status:', event.data);
          handleRaceStatusUpdate(event.data);
        }
      }

      function timer() {
        if (scope.isRaceActive && scope.startTime) {
          scope.runningTime = Date.now() - scope.startTime + tzOffset;
        }
        timerHandle = $timeout(timer, 100);
      }

      function getStatus() {
        clientApiService.getStatus()
          .then(function (response) {
            handleRaceStatusUpdate(response.data);
          });
      }

      function handleRaceStatusUpdate(raceStatus) {
        console.debug(raceStatus);
        scope.isRaceActive = raceStatus.state === 'ACTIVE';
        scope.raceEvent = raceStatus.event;
        if (raceStatus.state === 'ACTIVE') {
          scope.username = raceStatus.user.displayName;
          if (raceStatus.event === 'NONE') {
            scope.startTime = undefined;
            scope.splitTime = undefined;
            scope.runningTime = undefined;
            scope.finishTime = undefined;
          } else if (raceStatus.event === 'START') {
            scope.startTime = Date.now() - raceStatus.currentTime;
            scope.splitTime = undefined;
            scope.runningTime = undefined;
            scope.finishTime = undefined;
          } else if (raceStatus.event === 'SPLIT') {
            scope.startTime = Date.now() - raceStatus.currentTime;
            scope.splitTime = raceStatus.splitTime - raceStatus.startTime + tzOffset;
            scope.startTime = Date.now() - scope.splitTime + tzOffset;
          } else if (raceStatus.event === 'FINISH') {
            scope.startTime = Date.now() - raceStatus.currentTime;
            scope.finishTime = raceStatus.finishTime - raceStatus.startTime + tzOffset;
            $timeout(function(){updateLastRace();},1000);
          }
        } else if (raceStatus.splitTime && raceStatus.startTime && raceStatus.finishTime) {
          scope.splitTime = raceStatus.splitTime - raceStatus.startTime + tzOffset;
          scope.finishTime = raceStatus.finishTime - raceStatus.startTime + tzOffset;
          $timeout(function(){updateLastRace();},1000);
        }
      }

      function updateLastRace() {
        clientApiService.getLastRace()
          .then(function (response) {
            if (response.data && response.data.user) {
              scope.lastRace = {
                user: response.data.user.displayName,
                finishTime: response.data.finishTime - response.data.startTime + tzOffset,
                splitTime: response.data.splitTime - response.data.startTime + tzOffset
              };
            }
          });
      }
    }
  }]);

}());
