import { createStandardAction } from "typesafe-actions";
import { UserResult } from "./Leaderboard";

export const backendEventChannelReceivedMessage = createStandardAction('BackendEventChannelReceivedMessage')<BackendEvent>();

export const getLeaderboard = createStandardAction('GetLeaderboard')<UserResult[]>();