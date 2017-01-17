import {Response} from "@angular/http";

export class FailureInfo {
  constructor(public readonly message: string, public readonly response: Response) {
  }
}
