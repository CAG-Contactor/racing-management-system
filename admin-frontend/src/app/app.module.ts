import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpModule } from '@angular/http';
import { MaterialModule } from '@angular/material';
import { RouterModule, Routes } from '@angular/router';


import { AppComponent } from './app.component';
import { DevlabComponent } from './devlab/devlab.component';
import { MainComponent } from './main/main.component';
import { RacesComponent } from './races/races.component';
import { UsersComponent } from './users/users.component';
import { ServicesComponent } from './services/services.component';


const appRoutes: Routes = [
  { path: 'main', component: MainComponent },
  { path: 'races', component: RacesComponent },
  { path: 'users', component: UsersComponent },
  { path: 'services', component: ServicesComponent },
  { path: 'dev-lab', component: DevlabComponent },
  { path: '',   redirectTo: '/main', pathMatch: 'full' }
];

@NgModule({
  declarations: [
    AppComponent,
    DevlabComponent,
    MainComponent,
    RacesComponent,
    UsersComponent,
    ServicesComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpModule,
    MaterialModule.forRoot(),
    RouterModule.forRoot(appRoutes)
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {

}
