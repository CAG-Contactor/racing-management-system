import * as ng1 from 'angular';

export const appModule = ng1.module('app.module', []);

// Vi måste göra importen efter att vi definierat modulen.
import "./main";
import "./overview";

console.debug('appModule is initialized:', appModule);
