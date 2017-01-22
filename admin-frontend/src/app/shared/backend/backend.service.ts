import {Injectable} from "@angular/core";
import {Http, Response, URLSearchParams} from "@angular/http";
import {Observable} from "rxjs";

import {environment} from "../../../environments/environment";
import {Errors} from "../errors";
import {UserResult} from "./user-result";
import {User} from "./user";

@Injectable()
export class Backend {
  private backendUrlBase = environment.bsEnv.API;

  constructor(private http:Http, private errors:Errors) {}

  getUsers(): Promise<User[]> {
    return this.http.get(this.backendUrlBase + 'admin/users/')
      .map(r => r.json())
      .toPromise()
      .catch(err => this.handleError(err));
  }

  getUserResults(): Promise<UserResult[]> {
    return this.http.get(this.backendUrlBase + 'admin/registered-races/')
      .map(r => r.json())
      .toPromise()
      .catch(err => this.handleError(err));
  }

  removeUserResult(userResult:UserResult):Promise<void> {
    return this.http.delete(this.backendUrlBase + 'admin/registered-races/'+userResult.id)
      .map(() => <void>undefined)
      .toPromise()
      .catch(err => this.handleError<void>(err));
  }

  cancelCurrentRace():Observable<void> {
    return this.http.delete(this.backendUrlBase + 'admin/cancel-current-race/')
      .map(() => <void>undefined)
      .catch(err => this.handleError<void>(err));
  }

  login(user:string, password:string):Observable<void> {
    console.debug('login',name,password);
    const params = new URLSearchParams();
    params.append('user',user);
    params.append('password',password);
    return this.http.get(this.backendUrlBase + 'admin/login', {search:params})
      .map(resp => {
        localStorage.setItem("cag-admin-token", resp.headers.get('x-cag-token'));
        return undefined;
      });
  }

  private handleError<T>(error:Response|any): Observable<T> {
    console.error('Error: ', error);
    this.errors.notify('Fel vid anrop till admin backend', error);
    return Observable.throw(undefined);
  }
}
