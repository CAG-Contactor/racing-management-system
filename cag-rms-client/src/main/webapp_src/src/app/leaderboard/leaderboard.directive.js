"use strict";
angular.module('cag-rms-client').directive('leaderboard', function ($log) {
  $log.log("in leaderboard");
  return {
    restrict: 'E',
    templateUrl: 'leaderboard/leaderboard.tpl.html'
  };
});