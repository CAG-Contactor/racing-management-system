'use strict';
(function () {
    angular.module('cag-rms-client').directive('currentRace', ['$timeout', function ($timeout) {
        return {
            scope: {},
            restrict: 'E',
            link: function (scope, elems, attrs) {
                scope.currentUser = {name: 'Nisse', startTime: 0, splitTime: new Date(), finishTime: new Date()};
                console.log("time started");
                var start = new Date().getTime();
                timer();

                function timer() {
                    var now = new Date().getTime();
                    scope.currentUser.startTime = now - start;
                    $timeout(timer, 337);
                }
            },

            templateUrl: 'currentrace/currentrace.tpl.html'
        };
    }]);
}());
