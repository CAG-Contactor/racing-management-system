import { combineReducers } from "redux";
import { backendEventChannelReducer } from "./backend-event-channel/backend-event-channel.state";
import {queueEventChannelReducer} from "./queue/queue-event-channel-reducer";

export const updateState = combineReducers({
  backendEventChannelState: backendEventChannelReducer,
  queueEventChannelReducer
});