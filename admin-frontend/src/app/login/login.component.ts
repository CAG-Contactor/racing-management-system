import {Component} from "@angular/core";
import {Observable} from "rxjs";

import {Backend} from "../shared";

/**
 *  This class represents the lazy loaded LoginComponent.
 */

@Component({
  selector: 'login-cmp',
  templateUrl: './login.component.html'
})
export class LoginComponent {
  name:string;
  password:string;
  constructor(private backend:Backend){}
  login() {
    this.backend.login(this.name, this.password)
      .catch(err => {
        alert('Failed login '+ err);
        return Observable.throw(undefined);
      })
      .subscribe(resp => alert('Successful login:'+resp));

  }
}
