import { createStandardAction } from "typesafe-actions";

export const getCurrentStatus = createStandardAction('GetCurrentStatus')<string, string>();

export const setUser = createStandardAction('SetUser')<string, string>();
