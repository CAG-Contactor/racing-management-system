'use strict';
(function () {
    angular.module('cag-rms-client').directive('currentRace', ['$timeout', function ($timeout) {
        return {
            scope: {},
            restrict: 'E',
            link: function (scope, elems, attrs) {
                scope.currentUser = {name: 'Nisse', startTime: Date.now(), splitTime: new Date(), finishTime: new Date()};
                scope.startTimeInMillis = +scope.currentUser.startTime;
                console.log("time started");
                timer();

                function timer() {
                    scope.currentUser.startTime = Date.now() - scope.startTimeInMillis;
                    $timeout(timer, 100);
                }
            },
            templateUrl: 'currentrace/currentrace.tpl.html'
        };
    }]);
}());
