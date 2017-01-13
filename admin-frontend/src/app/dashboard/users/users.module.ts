import {NgModule} from "@angular/core";
import {RouterModule} from "@angular/router";
import {UsersComponent} from "./users.component";
import {BrowserModule} from "@angular/platform-browser";

@NgModule({
  imports: [RouterModule,BrowserModule],
  declarations: [UsersComponent],
  exports: [UsersComponent]
})

export class UsersModule {
}
