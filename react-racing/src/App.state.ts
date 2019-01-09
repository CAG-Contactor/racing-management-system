import {
  ActionType,
  createStandardAction,
  getType
} from 'typesafe-actions';

const changeLoginStatus = createStandardAction('ChangeLoginStatus')<boolean>();
export const appActionCreators = {
  changeLoginStatus
};

export type AppAction = ActionType<typeof changeLoginStatus>

export interface AppState {
  readonly isLoggedIn: boolean;
}

const INIT_STATE: AppState = {
  isLoggedIn: false
};

export function appStateReducer(currentState: AppState = INIT_STATE, action: AppAction) {
  switch (action.type) {
    case getType(changeLoginStatus):
      return {
        ...currentState,
        isLoggedIn: action.payload
      };
    default:
      return currentState;

  }
}

