import {Component} from "@angular/core";
import {Backend} from "../shared";
import {Router} from "@angular/router";

/**
 *  This class represents the lazy loaded LoginComponent.
 */

@Component({
  selector: 'login-cmp',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']

})
export class LoginComponent {
  user:string;
  password:string;
  loginFailed:boolean;

  constructor(private readonly backend:Backend, private readonly router: Router){}

  login() {
    this.loginFailed = false;
    this.backend.login(this.user, this.password)
      .then(() => this.router.navigateByUrl('/dashboard/home'))
      .catch(() => {
        this.loginFailed = true;
      });
  }
}
