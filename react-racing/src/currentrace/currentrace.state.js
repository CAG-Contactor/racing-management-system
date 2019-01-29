

const INIT_STATE = {
  currentrace: []
};

export function currentraceReducer(oldState = INIT_STATE, action) {
  switch (action.type) {
    case 'GetCurrentrace':
      return {
        ...oldState,
        currentrace: action.payload
    }
    default:
      return oldState;
  }
}
