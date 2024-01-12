import {createStandardAction} from "typesafe-actions";
import {UserResult} from "../leaderboard/Leaderboard";

export const getMyRaces = createStandardAction('GetMyRaces')<UserResult[]>();
