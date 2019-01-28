import { createStandardAction } from "typesafe-actions";
import {User} from "../backend-event-channel/user";

export const getUserQueue = createStandardAction('GetUserQueue')<User[]>();
export const addToUserQueue = createStandardAction('AddToUserQueue')<User[]>();
