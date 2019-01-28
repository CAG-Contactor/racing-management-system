import {
    ActionType,
    getType
} from "typesafe-actions";
import * as actions from './queue.actions';
import {User} from "../backend-event-channel/user";

export type UserQueueActions = ActionType<typeof actions>

export interface UserQueueState {
    userQueue: User[]
}

const INIT_STATE: UserQueueState = {
    userQueue: []
};

export function userQueueReducer(oldState: UserQueueState = INIT_STATE, action: UserQueueActions): UserQueueState {
    switch (action.type) {
        case getType(actions.getUserQueue):
            return {
                ...oldState,
                userQueue: action.payload
            }
        case getType(actions.addToUserQueue):
            return {
                ...oldState,
                userQueue: action.payload
            }
        default:
            return oldState;
    }
}