import {NgModule} from "@angular/core";
import {CommonModule} from "@angular/common";
import {CarouselModule, DropdownModule, AlertModule} from "ng2-bootstrap";

import {HomeComponent, NotificationComponent} from "./home.component";

@NgModule({
  imports: [CommonModule, CarouselModule, DropdownModule, AlertModule],
  declarations: [HomeComponent, NotificationComponent],
  exports: [HomeComponent, NotificationComponent]
})

export class HomeModule {
}
