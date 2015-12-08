'use strict';

import angular from 'angular';
import exampleDirective from './ExampleDirective';

const exampleModule = angular.module('ExampleModule', []);
exampleModule.directive(exampleDirective.selector, exampleDirective);

console.debug('Initialized SubModule1:',exampleModule);
export default exampleModule;
