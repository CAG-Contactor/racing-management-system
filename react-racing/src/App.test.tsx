import * as React from 'react';
import * as ReactDOM from 'react-dom';
import App from './App';
import {BackendEventChannel} from './backend-event-channel/backend-event-channel';

it('renders without crashing', () => {
  const websocket: BackendEventChannel = {} as BackendEventChannel;
  const div = document.createElement('div');
  ReactDOM.render(<App websocket={websocket}/>, div);
  ReactDOM.unmountComponentAtNode(div);
});
