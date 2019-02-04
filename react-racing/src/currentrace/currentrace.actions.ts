import { createStandardAction } from "typesafe-actions";
import { RaceStatus } from './currentrace'

export const getRaceStatus = createStandardAction('GetRaceStatus')<RaceStatus, string>();

export const setRunningTime = createStandardAction('SetRunningTime')<string>();

export const setFinishTime = createStandardAction('SetFinishTime')<string>(); 

export const setSplitTime = createStandardAction('SetSplitTime')<string>(); 

export const setUsername = createStandardAction('SetUsername')<string>(); 

export const getLastRace = createStandardAction('GetLastRace')<string>(); 

export const setRaceEvent = createStandardAction('SetRaceEvent')<string>(); 

export const setStartTime = createStandardAction('SetStartTime')<string>(); 

