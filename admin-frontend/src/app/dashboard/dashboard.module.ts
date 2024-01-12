import {NgModule} from "@angular/core";
import {CommonModule} from "@angular/common";
import {RouterModule} from "@angular/router";
import {DropdownModule, ModalModule} from "ng2-bootstrap";

import {SidebarComponent, TopNavComponent} from "../shared";
import {HomeModule} from "./home";
import {ResultModule} from "./result";
import {UsersModule} from "./users";

import {DashboardComponent} from "./dashboard.component";
import {DashboardRoutingModule} from "./dashboard.routes";

@NgModule({
  imports: [
    DashboardRoutingModule,
    CommonModule,
    RouterModule,
    DropdownModule,
    ModalModule,
    HomeModule,
    ResultModule,
    UsersModule
  ],
  declarations: [DashboardComponent, TopNavComponent, SidebarComponent],
  exports: [DashboardComponent, TopNavComponent, SidebarComponent]
})

export class DashboardModule {
}
