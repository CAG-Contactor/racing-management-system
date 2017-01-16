import {Component} from "@angular/core";
import * as $ from "jquery";
import {Errors, FailureInfo} from "../errors";

@Component({
  selector: 'top-nav',
  templateUrl: './topnav.html',
  styleUrls: ['./topnav.scss']
})

export class TopNavComponent {
  alarms: FailureInfo[] = [];

  constructor(private errors: Errors) {
    this.errors.getErrors()
      .subscribe(error => this.alarms.push(error))
  }

  message(alarm: FailureInfo): string {
    return alarm.message
  }

  details(alarm: FailureInfo): string {
    return alarm.response &&
      alarm.response.url + ' => ' + alarm.response.status + ' ' + alarm.response.statusText
      ||
      "Inga detaljer";
  }

  remove(alarmToRemove: FailureInfo): void {
    if (alarmToRemove === undefined) {
      this.alarms = [];
    } else {
      this.alarms = this.alarms.filter(f => f !== alarmToRemove);
    }
  }

  rtl(): void {
    var body: any = $('body');
    body.toggleClass('rtl');
  }

  sidebarToggler(): void {
    var sidebar: any = $('#sidebar');
    var mainContainer: any = $('.main-container');
    sidebar.toggleClass('sidebar-left-zero');
    mainContainer.toggleClass('main-container-ml-zero');
  }
}
