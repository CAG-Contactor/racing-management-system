"use strict";
angular.module('cag-rms-client').directive('leaderboard', function ($log, $http) {
  $log.log("in leaderboard");
  $http({
    method: 'GET',
    url: 'http://localhost:10180/results'
  }).then(function successCallback(response) {
    console.debug(response.data);
  }, function errorCallback(response) {
    // called asynchronously if an error occurs
    // or server returns response with an error status.
  });

  return {
    restrict: 'E',
    templateUrl: 'leaderboard/leaderboard.tpl.html'
  };
});