import * as React from 'react';
import * as ReactDOM from 'react-dom';
import App from './App';
import 'bootstrap';
import 'bootstrap/dist/css/bootstrap.min.css';
import './index.css';
import { Provider } from 'react-redux';
import { createStore } from 'redux';
import { BackendEventChannel } from './backend-event-channel/backend-event-channel';
import { composeWithDevTools, } from 'redux-devtools-extension';
import { rootReducer } from './state';

const store = createStore(
  rootReducer,
  composeWithDevTools()
);

ReactDOM.render(
  <Provider store={store}>
    <App websocket={new BackendEventChannel(store)}/>
  </Provider>
  ,
  document.getElementById('root') as HTMLElement
);
