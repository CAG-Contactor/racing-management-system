import {Route, RouterModule} from "@angular/router";
import {HomeRoutes} from "./home/index";
import {ResultRoutes} from "./result/index";
import {DashboardComponent} from "./index";
import {NgModule} from "@angular/core";

const dashboardRoutes: Route[] = [
  {
    path: 'dashboard',
    component: DashboardComponent,
    children: [
      ...HomeRoutes,
      ...ResultRoutes
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
