'use strict';

describe('CurrentRaceDirective view', function () {
  var $compile, $rootScope, $httpBackend;

  beforeEach(module('cag-rms-client'));

  beforeEach(inject(function (_$compile_, _$rootScope_, $injector) {
    $compile = _$compile_;
    $rootScope = _$rootScope_;
    // Get mocked $httpBackend
    $httpBackend = $injector.get('$httpBackend');
  }));

  afterEach(function () {
    $httpBackend.verifyNoOutstandingExpectation();
    $httpBackend.verifyNoOutstandingRequest();
  });

  describe('Status-logic', function () {
    it('Status.INACTIVE should result in Klart', function () {
      $httpBackend.expect('GET', 'http://localhost:10080/status');
      $httpBackend.when('GET', 'http://localhost:10080/status').respond({state: 'INACTIVE'});

      // Compile a piece of HTML containing the directive
      var element = $compile("<current-race></current-race>")($rootScope);
      //Triggar http-mocken?
      $httpBackend.flush();

      expect(element.html()).toContain("Klart");
    });

    it('Status.ACTIVE should result in Pågår!', function () {
      $httpBackend.expect('GET', 'http://localhost:10080/status');
      $httpBackend.when('GET', 'http://localhost:10080/status').respond({state: 'ACTIVE'});

      // Compile a piece of HTML containing the directive
      var element = $compile("<current-race></current-race>")($rootScope);
      //Triggar http-mocken?
      $httpBackend.flush();

      expect(element.html()).toContain("Pågår!");
    });
  });
});
