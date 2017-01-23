import {Component} from "@angular/core";
import {Backend} from "../shared";
import {Router} from "@angular/router";

/**
 *  This class represents the lazy loaded LoginComponent.
 */

@Component({
  selector: 'login-cmp',
  templateUrl: './login.component.html'

})
export class LoginComponent {
  user:string;
  password:string;

  constructor(private readonly backend:Backend, private readonly router: Router){}

  login() {
    this.backend.login(this.user, this.password)
      .then(() => this.router.navigateByUrl('/dashboard/home'))
      .catch(err => {
        alert('Failed login '+ err);
        this.router.navigateByUrl('/dashboard/home');
      });
  }
}
