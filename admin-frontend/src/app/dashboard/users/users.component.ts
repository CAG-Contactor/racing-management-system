import {Component, OnInit} from "@angular/core";
import {User, Backend} from "../../shared";

@Component({
  selector: 'app-users',
  templateUrl: './users.component.html',
  styleUrls: ['users.component.scss']
})
export class UsersComponent implements OnInit {
  users: User[] = [];

  constructor(private backend:Backend) {
  }

  ngOnInit() {
    this.backend.getUsers()
      .then(users => this.users = users);
  }

  save():void {
    this.backend.downloadUsersCsvFile()
      .then(data => window.open(window.URL.createObjectURL(data)));
  }

}
