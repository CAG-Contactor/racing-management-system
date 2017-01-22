import {Component, OnInit} from "@angular/core";

import {Backend, UserResult, User} from "../../shared";

@Component({
  selector: 'result-cmp',
  templateUrl: './result.component.html',
  styleUrls: ['result.component.scss']
})
export class ResultComponent implements OnInit {
  userResults:UserResult[] = [];

  constructor(private backend:Backend) {
  }

  ngOnInit() {
    this.userResults = [
      new UserResult("a123", 1234, new User("123", "Kaka"), 5678, 1234, 'WALKOVER')
    ];

    console.debug("", this.backend.toString());
    // this.backend.getUserResults()
    //   .subscribe(ur => this.userResults = ur);
  }

  remove(result:UserResult):void {
    // Confirm dialog first
    console.debug('Call remove: ', result);
  }

}
