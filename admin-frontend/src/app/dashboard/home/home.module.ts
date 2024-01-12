import {NgModule} from "@angular/core";
import {CommonModule} from "@angular/common";
import {AlertModule, CarouselModule, DropdownModule} from "ng2-bootstrap";

import {HomeComponent, NotificationComponent} from "./home.component";

@NgModule({
  imports: [CommonModule, CarouselModule, DropdownModule, AlertModule],
  declarations: [HomeComponent, NotificationComponent],
  exports: [HomeComponent, NotificationComponent]
})

export class HomeModule {
}
