import * as ng1 from 'angular';
import {appModule} from "./app.module";

ng1.bootstrap(document, [appModule.name], {
  strictDi: true // Vi kräver att man använder explicit dependency injection
});
