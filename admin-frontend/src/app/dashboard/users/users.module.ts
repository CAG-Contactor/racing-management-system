import {NgModule} from "@angular/core";
import {RouterModule} from "@angular/router";
import {BrowserModule} from "@angular/platform-browser";

import {UsersComponent} from "./users.component";

@NgModule({
  imports: [RouterModule,BrowserModule],
  declarations: [UsersComponent],
  exports: [UsersComponent]
})

export class UsersModule {
}
