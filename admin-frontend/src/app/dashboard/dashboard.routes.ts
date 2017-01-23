import {Route, RouterModule} from "@angular/router";
import {NgModule} from "@angular/core";
import {HomeRoutes} from "./home";
import {ResultRoutes} from "./result";
import {DashboardComponent} from "./dashboard.component";
import {UsersRoutes} from "./users";

const dashboardRoutes: Route[] = [
  {
    path: 'dashboard',
    component: DashboardComponent,
    children: [
      ...HomeRoutes,
      ...ResultRoutes,
      ...UsersRoutes
    ],
  },
  {
    path: '', redirectTo: 'home', pathMatch: 'full'
  }
];
@NgModule({
  imports: [
    RouterModule.forChild(dashboardRoutes)
  ],
  exports: [
    RouterModule
  ]
})
export class DashboardRoutingModule { }
