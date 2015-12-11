'use strict';
(function () {
    angular.module('cag-rms-client').directive('currentRace', ['$timeout', function ($timeout) {
        return {
            scope: {},
            restrict: 'E',
            link: function (scope, elems, attrs) {
                scope.currentUser = {name: 'Nisse', startTime: 0, splitTime: new Date(), finishTime: new Date()};
                console.log("time started");
                timer();

                function timer() {
                    scope.currentUser.startTime++;
                    $timeout(timer, 1);
                }
            },

            templateUrl: 'currentrace/currentrace.tpl.html'
        };
    }]);
}());
