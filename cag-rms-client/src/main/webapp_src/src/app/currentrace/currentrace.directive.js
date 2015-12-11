'use strict';
(function () {
    angular.module('cag-rms-client').directive('currentRace', ['$timeout', '$http', function ($timeout, $http) {
        return {
            scope: {},
            restrict: 'E',
            link: function (scope, elems, attrs) {
                $http({
                    method: 'GET',
                    url: 'http://localhost:10080/status'
                }).then(function successCallback(response) {
                    console.debug(response.data);
                    scope.isActive = response.data.state === 'ACTIVE';
                    if (response.data.startTime) {
                        scope.startTime = new Date(response.data.startTime);
                    }
                    else {
                        scope.startTime = new Date();
                    }
                    scope.startTimeInMillis = +scope.startTime;

                    if (response.data.middleTime) {
                        scope.splitTime = new Date(response.data.middleTime);
                    }
                    if (response.data.finishTime) {
                        scope.finishTime = new Date(response.data.finishTime);
                    }
                }, function errorCallback(response) {
                });
                //scope.currentUser = {name: 'Nisse', startTime: Date.now(), splitTime: new Date(), finishTime: new Date()};
                //scope.startTimeInMillis = +scope.currentUser.startTime;
                console.log("time started");
                timer();

                function timer() {
                    if (scope.isActive) {
                        scope.startTime = Date.now() - scope.startTimeInMillis;
                        $timeout(timer, 100);
                    }
                }
            },
            templateUrl: 'currentrace/currentrace.tpl.html'
        };
    }]);
}());
