import * as actions from './currentrace.actions';
import {
  ActionType,
  getType
} from "typesafe-actions";

const INIT_STATE = {
  currentrace: ['a', 'b']
};

export function currentraceReducer(oldState = INIT_STATE, action) {
  switch (action.type) {
    case 'GetCurrentrace':
      return {
        ...oldState,
        currentrace: action.payload
    }
    default:
      return oldState;
  }
}
