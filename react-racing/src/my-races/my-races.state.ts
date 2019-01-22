import { UserResult } from "../leaderboard/Leaderboard";
import * as actions from './my-races.actions';
import {
  ActionType,
  getType
} from "typesafe-actions";

export type MyRacesActions = ActionType<typeof actions>

export interface MyRacesState {
  myRaces: UserResult[]
}

const INIT_STATE: MyRacesState = {
  myRaces: []
};

export function myRacesReducer(oldState: MyRacesState = INIT_STATE, action: MyRacesActions): MyRacesState {
  switch (action.type) {
    case getType(actions.getMyRaces):
      return {
        ...oldState,
        myRaces: action.payload
    }
    default:
      return oldState;
  }
}
