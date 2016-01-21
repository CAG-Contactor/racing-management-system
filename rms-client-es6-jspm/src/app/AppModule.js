'use strict';

import angular from 'angular';
import exampleModule from '../examplemodule/ExampleModule';
import cagAppDirective from './CagAppDirective';

const appModule = angular.module('AppModule', [exampleModule.name]);
appModule.directive(cagAppDirective.selector, cagAppDirective);


console.debug('Initialized AppModule:', appModule);
export default appModule;
