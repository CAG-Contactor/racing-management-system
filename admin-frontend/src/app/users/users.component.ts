import { Component, OnInit } from '@angular/core';
import User from "./User";

@Component({
  selector: 'app-users',
  templateUrl: './users.component.html',
  styleUrls: ['./users.component.css']
})
export class UsersComponent implements OnInit {
  users:User[] = [{displayName:"Kaka",userId:"kaka@banan"},{displayName:"Biffen",userId:"biffen@bullen"}];
  constructor() { }

  ngOnInit() {
  }

}
