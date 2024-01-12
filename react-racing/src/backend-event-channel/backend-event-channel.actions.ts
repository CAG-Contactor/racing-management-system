import {createStandardAction} from "typesafe-actions";

export const backendEventChannelHasOpened = createStandardAction('BackendEventChannelHasOpened')<void>();
export const backendEventChannelHasClosed = createStandardAction('BackendEventChannelClosed')<void>();
export const backendEventChannelGotError = createStandardAction('BackendEventChannelGotError')<ErrorEvent>();
export const backendEventChannelReceivedMessage = createStandardAction('BackendEventChannelReceivedMessage')<BackendEvent>();
