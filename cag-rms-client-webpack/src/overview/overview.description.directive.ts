'use strict';
import * as ng1 from "angular";

export const appModule: ng1.IModule = ng1.module('app.module');

const directiveFactory: ng1.Injectable<ng1.IDirectiveFactory> =
  () => ({
    scope: true,
    restrict: 'E',
    templateUrl: './overview.description.tpl.html',
    replace: true
  });

appModule.directive('overviewDescription', directiveFactory);

