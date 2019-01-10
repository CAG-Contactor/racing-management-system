import {
  ActionType,
  createStandardAction,
  getType
} from 'typesafe-actions';
import { User } from './backend-event-channel/user';

export interface LoginStatus {
  loggedIn: boolean;
  user?: User
}

export const changeLoginStatus = createStandardAction('ChangeLoginStatus')<LoginStatus>();
export const appActionCreators = {
  changeLoginStatus
};

export type AppAction = ActionType<typeof changeLoginStatus>

export interface AppState {
  readonly isLoggedIn: boolean;
  readonly user?: User;
}

const INIT_STATE: AppState = {
  isLoggedIn: false
};

export function appStateReducer(currentState: AppState = INIT_STATE, action: AppAction) {
  switch (action.type) {
    case getType(changeLoginStatus):
      return {
        ...currentState,
        isLoggedIn: action.payload.loggedIn,
        user: action.payload.user
      };
    default:
      return currentState;

  }
}

