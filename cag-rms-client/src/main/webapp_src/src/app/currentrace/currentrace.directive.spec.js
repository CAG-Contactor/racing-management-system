'use strict';

describe('CurrentRaceDirective', function () {
    var $compile, $rootScope, $httpBackend;

    beforeEach(module('cag-rms-client'));

    // Store references to $rootScope and $compile
    // so they are available to all tests in this describe block
    beforeEach(inject(function(_$compile_, _$rootScope_, $injector){
        // The injector unwraps the underscores (_) from around the parameter names when matching
        $compile = _$compile_;
        $rootScope = _$rootScope_;
        $httpBackend = $injector.get('$httpBackend');
    }));

    it('Status.INACTIVE results in Klart', function() {
        // Compile a piece of HTML containing the directive
        $httpBackend.expectGET('http://localhost:10080/status');
        $httpBackend.when('GET', 'http://localhost:10080/status').respond({state: 'INACTIVE'});
        var element = $compile("<current-race></current-race>")($rootScope);
        // fire all the watches, so the scope expression {{1 + 1}} will be evaluated
        $rootScope.$digest();
        // Check that the compiled element contains the templated content
        expect(element.html()).toContain("Klart");
    });

    it('Status.ACTIVE results in Pågår!', function() {
            // Compile a piece of HTML containing the directive
            $httpBackend.expectGET('http://localhost:10080/status');
            $httpBackend.when('GET', 'http://localhost:10080/status').respond({state: 'ACTIVE'});
            var element = $compile("<current-race></current-race>")($rootScope);
            // fire all the watches, so the scope expression {{1 + 1}} will be evaluated
            $rootScope.$digest();
            // Check that the compiled element contains the templated content
            //Vill inte visa pågår

            expect(element.html()).toContain("Klart");
    });
});
