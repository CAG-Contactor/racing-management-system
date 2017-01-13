import {NgModule} from "@angular/core";
import {CommonModule} from "@angular/common";
import {RouterModule} from "@angular/router";
import {DropdownModule, ModalModule} from "ng2-bootstrap";
import {HomeModule} from "./home/home.module";
import {ResultModule} from "./result/result.module";
import {DashboardComponent} from "./dashboard.component";
import {TopNavComponent, SidebarComponent} from "../shared/index";
import {DashboardRoutingModule} from "./dashboard.routes";


@NgModule({
  imports: [
    DashboardRoutingModule,
    CommonModule,
    RouterModule,
    DropdownModule,
    ModalModule,
    HomeModule,
    ResultModule
  ],
  declarations: [DashboardComponent, TopNavComponent, SidebarComponent],
  exports: [DashboardComponent, TopNavComponent, SidebarComponent]
})

export class DashboardModule {
}
