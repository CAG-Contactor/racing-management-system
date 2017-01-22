import {NgModule} from "@angular/core";
import {BrowserModule} from "@angular/platform-browser";
import {RouterModule} from "@angular/router";

import {ResultComponent} from "./result.component";

@NgModule({
  imports: [RouterModule, BrowserModule],
  declarations: [ResultComponent],
  exports: [ResultComponent]
})

export class ResultModule {
}
