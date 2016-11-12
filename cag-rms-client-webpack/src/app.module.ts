import * as ng1 from 'angular';

export const appModule = ng1.module('app.module', []);

//import "./clock/clock.component"; // Vi måste göra importen efter att vi definierat modulen.

console.debug('appModule is initialized:', appModule);
