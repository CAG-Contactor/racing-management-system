import {Injectable} from "@angular/core";
import {Http, Response, Headers, RequestOptionsArgs} from "@angular/http";
import {Observable} from "rxjs";
import {environment} from "../../../environments/environment";
import {Errors} from "../errors";
import {UserResult} from "./user-result";
import {User} from "./user";
import {BackendServiceStatus} from "./backend-service-status";

@Injectable()
export class Backend {
  private backendUrlBase = environment.bsEnv.API;
  private currentUser: User;

  constructor(private http: Http, private errors: Errors) {
  }

  getUsers(): Promise<User[]> {
    const p = this.http.get(this.backendUrlBase + 'users/', this.optionsWithHeaders())
      .map(r => r.json())
      .toPromise();
    p.catch(err => this.handleError(err));
    return p;
  }

  getUserResults(): Promise<UserResult[]> {
    const p = this.http.get(this.backendUrlBase + 'registered-races/', this.optionsWithHeaders())
      .map(r => r.json())
      .toPromise();
    p.catch(err => this.handleError(err));
    return p;
  }

  removeUserResult(userResult: UserResult): Promise<void> {
    let p = this.http.delete(this.backendUrlBase + 'registered-races/' + userResult.id, this.optionsWithHeaders())
      .map(() => <void>undefined)
      .toPromise();
    p.catch(err => this.handleError<void>(err));
    return p;
  }

  cancelCurrentRace(): Promise<void> {
    let p = this.http.post(this.backendUrlBase + 'cancel-active-race/', undefined, this.optionsWithHeaders())
      .map(() => <void>undefined)
      .toPromise();
    p.catch(err => this.handleError<void>(err));
    return p;
  }

  login(user: string, password: string): Promise<User> {
    console.debug('login', user, password);
    const headers = new Headers();
    headers.append('x-cag-user', user);
    headers.append('x-cag-password', password);
    let p = this.http.post(this.backendUrlBase + 'login', undefined, {headers: headers})
      .map(resp => {
        this.currentUser = resp.json();
        localStorage.setItem("cag-admin-token", resp.headers.get('x-cag-token'));
        return this.currentUser;
      })
      .toPromise();
    p.catch(err => this.handleError<void>(err));

    return p;
  }

  logout(): void {
    localStorage.setItem("cag-admin-token", undefined);
    this.currentUser = undefined;
  }

  getCurrentUser(): User {
    return this.currentUser;
  }

  getServices(): Promise<BackendServiceStatus[]> {
    const p = this.http.get(this.backendUrlBase + 'status/', this.optionsWithHeaders())
      .map(r => r.json())
      .toPromise();
    p.catch(err => this.handleError(err));
    return p;
  }

  downloadResultsCsvFile(): Promise<Blob> {
    const url = this.backendUrlBase + 'registered-races';
    return this.downloadCsvFile(url);
  }

  downloadUsersCsvFile(): Promise<Blob> {
    const url = this.backendUrlBase + 'users';
    return this.downloadCsvFile(url);
  }

  private downloadCsvFile(url: string) {
    const headers = new Headers();
    headers.append('accept', 'text/csv');
    headers.append('x-cag-token', localStorage.getItem("cag-admin-token"));
    let p = this.http.get(url, {headers: headers})
      .map(res => new Blob([res.text()], {type: 'text/csv'}))
      .toPromise();
    p.catch(this.handleError);
    return p;
  }

  private handleError<T>(error: Response|any): Observable<T> {
    console.error('Error: ', error);
    this.errors.notify('Fel vid anrop till admin backend', error);
    return Observable.throw(undefined);
  }

  private optionsWithHeaders():RequestOptionsArgs {
    const headers = new Headers();
    headers.append('x-cag-token', localStorage.getItem("cag-admin-token"));
    return {headers: headers};
  }
}
