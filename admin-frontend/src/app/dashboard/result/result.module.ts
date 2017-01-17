import {NgModule} from "@angular/core";
import {RouterModule} from "@angular/router";
import {ResultComponent} from "./result.component";

@NgModule({
  imports: [RouterModule],
  declarations: [ResultComponent],
  exports: [ResultComponent]
})

export class ResultModule {
}
