beforeEach(function() {
  'use strict';

  this.addMatchers({
    toBePromiseObject: function() {
      var result;

      function isPromiseObj(obj) {
        // If is object
        if ( typeof obj === 'object' && obj !== null ) {
          return (
            typeof obj["then"] === 'function' &&
            typeof obj["finally"] === 'function' &&
            typeof obj["catch"] === 'function'
          );
        } else {
          return false;
        }
      }

      result = isPromiseObj(this.actual);

      this.message = function () {
        if ( !result ) {
          return "Expected " + this.actual + " to be a promise object.";
        }
      };

      return result;
    }
  });
});