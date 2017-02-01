import {Component} from "@angular/core";
import {Backend, BackendServiceStatus} from "../../shared";

@Component({
  selector: 'notifications-cmp',
  templateUrl: './notifications.html'
})
export class NotificationComponent {
}

@Component({
  selector: 'home-cmp',
  templateUrl: './home.component.html',
  styleUrls: ['home.component.scss']
})
export class HomeComponent {
  raceCancelledSucessfully: boolean = false;
  backendServices: BackendServiceStatus[] = [
    {name: 'Race administrator', alive: false},
    {name: 'Current race', alive: true}
  ]    .sort((s1, s2) => s1.name.localeCompare(s2.name));

  constructor(private readonly backend: Backend) {
  }

  abortCurrentRace(): void {
    this.backend.cancelCurrentRace()
      .then(() => {
        this.raceCancelledSucessfully = true;
        setTimeout(() => this.raceCancelledSucessfully = false, 5 * 1000);
      });
  }
}

