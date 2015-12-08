'use strict';

import htmlTemplate from './exampledirective.tpl.html!';

function factory() {
  return {
    restrict: 'E',
    template: htmlTemplate,
    controller: Controller,
    controllerAs: 'vm'
  }
}

factory.selector = 'exampleDirective';

function Controller() {
  const vm = this;
  console.debug('Created controller for:',factory.selector);
  vm.greeting = 'Häpp!'
}
export default factory;