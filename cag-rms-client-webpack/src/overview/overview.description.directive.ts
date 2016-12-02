'use strict';
import * as ng1 from "angular";
export const appModule: ng1.IModule = ng1.module('app.module');
const htmlTemplate = require("./overview.description.tpl.html");

const directiveFactory: ng1.Injectable<ng1.IDirectiveFactory> =
  () => ({
    scope: true,
    restrict: 'E',
    template: htmlTemplate,
    replace: true
  });

appModule.directive('overviewDescription', directiveFactory);

