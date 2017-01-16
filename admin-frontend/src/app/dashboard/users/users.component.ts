import {Component, OnInit} from "@angular/core";
import {User, Backend} from "../../shared/backend";

@Component({
  selector: 'app-users',
  templateUrl: './users.component.html',
  styleUrls: ['./users.component.css']
})
export class UsersComponent implements OnInit {
  users: User[] = [];

  constructor(private backend:Backend) {
  }

  ngOnInit() {
    this.backend.getUsers()
      .subscribe(users => this.users = users);
  }
}
