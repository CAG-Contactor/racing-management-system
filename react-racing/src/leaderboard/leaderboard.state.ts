import {UserResult} from "./Leaderboard";
import * as actions from './leaderboard.actions';
import {ActionType, getType} from "typesafe-actions";

export type LeaderboardActions = ActionType<typeof actions>

export interface LeaderboardState {
  leaderboard: UserResult[]
}

const INIT_STATE: LeaderboardState = {
  leaderboard: []
};

export function leaderboardReducer(oldState: LeaderboardState = INIT_STATE, action: LeaderboardActions): LeaderboardState {
  switch (action.type) {
    case getType(actions.getLeaderboard):
      return {
        ...oldState,
        leaderboard: action.payload
    }
    default:
      return oldState;
  }
}
