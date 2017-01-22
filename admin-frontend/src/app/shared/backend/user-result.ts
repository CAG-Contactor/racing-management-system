import {User} from "./user";

export type ResultType = 'FINISHED'|'WALKOVER'|'DISQUALIFIED';

export class UserResult {
  constructor(readonly id: string,
              readonly created: number,
              readonly user: User,
              readonly time: number,
              readonly splitTime: number,
              readonly result: ResultType) {}
}
