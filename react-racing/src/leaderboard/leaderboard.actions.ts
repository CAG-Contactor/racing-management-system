import { createStandardAction } from "typesafe-actions";
import { UserResult } from "./Leaderboard";

export const getLeaderboard = createStandardAction('GetLeaderboard')<UserResult[]>();
