import { createStandardAction } from "typesafe-actions";
import { User } from "./queue";

export const getUserQueue = createStandardAction('GetUserQueue')<User[]>();
