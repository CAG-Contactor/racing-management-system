import {
    ActionType,
    getType
} from "typesafe-actions";
import * as actions from '../backend-event-channel/backend-event-channel.actions';

export type ClientApiWebsocketActions = ActionType<typeof actions>

export interface QueueEventChannelState {
    queue: any[]
}


const INIT_STATE: QueueEventChannelState = {
    queue: []
};



export function queueEventChannelReducer(oldState: QueueEventChannelState = INIT_STATE, action: ClientApiWebsocketActions): QueueEventChannelState {
    const payload = getActionPayload(action)
    if (isQueueUpdatedEvent(action.type, payload)) {
        return {
            queue: [...oldState.queue, payload.data]
        }
    }
    return INIT_STATE
}

function getActionPayload(action: any) {
    return action.payload ? JSON.parse(action.payload) : {}
}

function isQueueUpdatedEvent(type: string, payload: any) {
    if(type === getType(actions.backendEventChannelReceivedMessage)) {
        return payload.eventType === 'QUEUE_UPDATED'
    }

    return false
}