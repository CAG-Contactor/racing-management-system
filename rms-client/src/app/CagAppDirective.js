'use strict';

import angular from 'angular';
import exampleModule from '../examplemodule/ExampleModule';
import htmlTemplate from './appview.tpl.html!';

function factory() {
  console.debug('Invoke cag-rm factory');
  return {
    restrict: 'E',
    template: htmlTemplate,
    controller: CagAppController,
    controllerAs: 'vm'
  }
}
factory.selector = 'cagApp';

function CagAppController() {
  const vm = this;
  vm.signIn = signIn;
  vm.signOut = signOut;

  function signIn(userid, password) {
    console.log('Sign in:', userid, password);
    vm.signedIn = true;
    vm.userid = userid;
  }

  function signOut() {
    console.log('Sign out');
    vm.signedIn = false;
    vm.userid = undefined;
  }
}


export default factory;

