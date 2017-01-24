import {NgModule} from "@angular/core";
import {BrowserModule} from "@angular/platform-browser";
import {RouterModule} from "@angular/router";
import {ModalModule} from "ng2-bootstrap";

import {ResultComponent} from "./result.component";

@NgModule({
  imports: [RouterModule, BrowserModule, ModalModule.forRoot()],
  declarations: [ResultComponent],
  exports: [ResultComponent]
})

export class ResultModule {
}
