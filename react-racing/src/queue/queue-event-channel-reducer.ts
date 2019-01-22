import {
    ActionType,
    getType
} from "typesafe-actions";
import * as actions from './queue.actions';
import {User} from "./queue";

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
        default:
            return oldState;
    }
}