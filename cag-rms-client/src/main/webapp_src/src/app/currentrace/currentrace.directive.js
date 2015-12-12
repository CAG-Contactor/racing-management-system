'use strict';
(function () {
    angular.module('cag-rms-client').directive('currentRace', ['$timeout', '$http', function ($timeout, $http) {
        return {
            scope: {},
            restrict: 'E',
            link: function (scope, elems, attrs) {
                var timerHandle, statusTimerHandle;

                scope.isRaceActive = false;
                scope.runningTime = 0;
                delete scope.startTime;
                delete scope.startTimeInMillis;
                delete scope.splitTime;
                delete scope.finishTime;

                if (attrs.hasOwnProperty('user')) {
                    scope.username = attrs['user'];
                }

                getStatus();
                timer();

                scope.$on(
                    '$destroy',
                    function (event) {
                        console.debug('destroy called');
                        $timeout.cancel(timerHandle);
                        $timeout.cancel(statusTimerHandle);
                    }
                );

                function timer() {
                    if (scope.isRaceActive && scope.startTimeInMillis) {
                        scope.runningTime = Date.now() - scope.startTimeInMillis;
                    }
                    timerHandle = $timeout(timer, 100);
                }

                function getStatus() {
                    $http({
                        method: 'GET',
                        url: 'http://localhost:10080/status'
                    }).then(function successCallback(response) {
                        console.debug(response.data);
                        scope.isRaceActive = response.data.state === 'ACTIVE';
                        scope.raceEvent = response.data.event;
                        if (response.data.startTime) {
                            scope.startTime = new Date(response.data.startTime);
                            scope.startTimeInMillis = +scope.startTime;
                        }
                        else {
                            delete scope.startTime;
                            delete scope.startTimeInMillis;
                        }
                        if (response.data.middleTime) {
                            scope.splitTime = new Date(response.data.middleTime);
                        }
                        else {
                            delete scope.splitTime;
                        }
                        if (response.data.finishTime) {
                            scope.finishTime = new Date(response.data.finishTime);
                            scope.runningTime = +scope.finishTime - +scope.startTime;
                        }
                        else {
                            delete scope.finishTime;
                        }
                    }, function errorCallback(response) {
                    });
                    statusTimerHandle = $timeout(getStatus, 1000);
                }
            },
            templateUrl: 'currentrace/currentrace.tpl.html'
        };

    }]);
}());
