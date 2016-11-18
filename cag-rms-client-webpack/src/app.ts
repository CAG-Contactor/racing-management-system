import * as ng1 from 'angular';
import {appModule} from "./app.module";
import "./assets/style/main.less";

ng1.bootstrap(document, [appModule.name], {
  strictDi: true // Vi kräver att man använder explicit dependency injection
});
