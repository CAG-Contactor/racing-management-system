import * as actions from './currentrace.actions';
import {
  ActionType,
  getType
} from "typesafe-actions";

export type RaceStatusActions = ActionType<typeof actions>

export interface RaceStatusState {
  raceStatus: any
  state: string
  uuid: string,
  runningTime: any,
  finishTime: any,
  splitTime: any,
  username: any,
  lastRace: any,
  event: any,
  startTime: any
}

const INIT_STATE: RaceStatusState = {
  raceStatus: {},
  state: 'NONE',
  uuid: '',
  runningTime: undefined,
  finishTime: "",
  splitTime: undefined,
  username: undefined,
  lastRace: undefined,
  event: undefined,
  startTime: undefined
};

export function currentraceReducer(oldState: RaceStatusState = INIT_STATE, action: RaceStatusActions): RaceStatusState {
  switch (action.type) {
    case getType(actions.getRaceStatus):
      return {
        ...oldState,
        raceStatus: action.payload,
        uuid: action.meta
      };
      case getType(actions.setRunningTime):
      return {
        ...oldState,
        runningTime: action.payload
      };
      case getType(actions.setFinishTime):
      return {
        ...oldState,
        finishTime: action.payload
      };
      case getType(actions.setSplitTime):
      return {
        ...oldState,
        splitTime: action.payload
      };
      case getType(actions.setUsername):
      return {
        ...oldState,
        username: action.payload
      };
      case getType(actions.getLastRace):
      return {
        ...oldState,
        lastRace: action.payload
      };
      case getType(actions.setRaceEvent):
      return {
        ...oldState,
        event: action.payload
      };
      case getType(actions.setStartTime):
      return {
        ...oldState,
        startTime: action.payload
      };
    default:
      return oldState;
  }
}
