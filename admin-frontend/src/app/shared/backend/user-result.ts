export type ResultType = 'FINISHED'|'WALKOVER'|'DISQUALIFIED';

export class UserResult {
  constructor(readonly id: string,
              readonly userId: string,
              readonly time: number,
              readonly splitTime: number,
              readonly resultType: ResultType) {}
}
