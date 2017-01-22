import {Routes} from "@angular/router";
import {LoginRoutes} from "./login";

export const routes: Routes = [
  ...LoginRoutes,
  { path: '',   redirectTo: '/login', pathMatch: 'full' }
];

console.debug('Routes initialized:', routes);
