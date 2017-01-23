import {Injectable} from "@angular/core";
import {Response} from "@angular/http";
import {ReplaySubject, Subject, Observable} from "rxjs";

import {FailureInfo} from "./FailureInfo";

@Injectable()
export class Errors {
  private errors:Subject<FailureInfo> = new ReplaySubject<FailureInfo>(1);

  getErrors():Observable<FailureInfo> {
    return this.errors.asObservable();
  }

  notify(errorMessage:string, response?:Response):void {
    this.errors.next(new FailureInfo(errorMessage, response))
  }
}
