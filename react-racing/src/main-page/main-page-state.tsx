import * as actions from './main-page-action';
import {
  ActionType,
  getType
} from "typesafe-actions";

export type MainPageActions = ActionType<typeof actions>

export interface MainPageState {
  currentStatus: any
  user: any;
  event: any;
}

const INIT_STATE: MainPageState = {
  currentStatus: undefined,
  user: undefined,
  event: undefined
};

export function mainPageReducer(oldState: MainPageState = INIT_STATE, action: MainPageActions): MainPageState {
  switch (action.type) {
    case getType(actions.getCurrentStatus):
      return {
        ...oldState,
        user: action.payload,
        event: action.meta
    };
    case getType(actions.setUser):
    return {
      ...oldState,
      user: action.payload,
      event: action.meta
  };
    default:
      return oldState;
  }
}
