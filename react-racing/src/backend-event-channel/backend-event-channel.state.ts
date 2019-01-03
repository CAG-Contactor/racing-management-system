import {
  ActionType,
  getType
} from "typesafe-actions";
import * as actions from './backend-event-channel.actions';

export type ClientApiWebsocketActions = ActionType<typeof actions>

export interface BackendEventChannelState {
  readonly isOpen: boolean;
  readonly error?: ErrorEvent;
  readonly lastReceivedEvent?: BackendEvent;
}

const INIT_STATE: BackendEventChannelState = {
  isOpen: false
};

export function backendEventChannelReducer(oldState: BackendEventChannelState = INIT_STATE, action: ClientApiWebsocketActions): BackendEventChannelState {
  switch (action.type) {
    case getType(actions.backendEventChannelHasOpened):
      return {
        ...oldState,
        isOpen: true
      };
    case getType(actions.backendEventChannelHasClosed):
      return {
        ...oldState,
        isOpen: false
      };
    case getType(actions.backendEventChannelGotError):
      return {
        ...oldState,
        error: action.payload
      };
    case getType(actions.backendEventChannelReceivedMessage):
      return {
        ...oldState,
        lastReceivedEvent: action.payload
      };
    default:
      return oldState;
  }
}