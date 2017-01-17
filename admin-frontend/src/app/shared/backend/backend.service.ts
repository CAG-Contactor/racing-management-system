import {Injectable} from "@angular/core";
import {Http, Response} from "@angular/http";
import {Observable} from "rxjs";
import {UserResult} from "./UserResult";
import {User} from "./User";
import {Errors} from "../errors/errors.service";

@Injectable()
export class Backend {
  private backendUrlBase = 'http://localhost:8080/';
  constructor(private http:Http, private errors:Errors) {}

  getUsers(): Observable<User[]> {
    return this.http.get(this.backendUrlBase + 'admin/users/')
      .map(r => r.json())
      .catch(err => this.handleError(err));
  }

  getUserResults(): Observable<UserResult[]> {
    return this.http.get(this.backendUrlBase + 'admin/registered-races/')
      .map(r => r.json())
      .catch(err => this.handleError(err));
  }

  removeUserResult(userResult:UserResult):Observable<void> {
    return this.http.delete(this.backendUrlBase + 'admin/registered-races/'+userResult.id)
      .map(() => <void>undefined)
      .catch(err => this.handleError<void>(err));
  }

  cancelCurrentRace():Observable<void> {
    return this.http.delete(this.backendUrlBase + 'admin/cancel-current-race/')
      .map(() => <void>undefined)
      .catch(err => this.handleError<void>(err));
  }

  private handleError<T>(error:Response|any): Observable<T> {
    console.error('Error: ', error);
    this.errors.notify('Fel vid anrop till admin backend', error);
    return Observable.throw(undefined);
  }
}
