import { combineReducers } from 'redux';
import { backendEventChannelReducer } from './backend-event-channel/backend-event-channel.state';
import { leaderboardReducer } from "./leaderboard/leaderboard.state";
import { StateType } from 'typesafe-actions';
import { DeepReadonly } from 'utility-types';
import { appStateReducer } from './App.state';
import { userQueueReducer } from "./queue/queue-event-channel-reducer";
import { myRacesReducer } from "./my-races/my-races.state";

export const rootReducer = combineReducers({
  appState: appStateReducer,
  backendEventChannelState: backendEventChannelReducer,
  leaderboardState: leaderboardReducer,
  userQueueState: userQueueReducer,
  myRacesState: myRacesReducer
});

export type RootState = DeepReadonly<StateType<typeof rootReducer>>;