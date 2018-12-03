import { combineReducers } from "redux";
import { backendEventChannelReducer } from "./backend-event-channel/backend-event-channel.state";

export const updateState = combineReducers({
  backendEventChannelState: backendEventChannelReducer
});