import * as React from 'react';
import './App.css';
import { BackendEventChannel } from "./backend-event-channel/backend-event-channel";
import logo from './logo.svg';

interface AppProps {
  websocket?: BackendEventChannel;
}

class App extends React.Component<AppProps, {}> {
  constructor(props: AppProps) {
    super(props);
  }

  public render() {
    return (
      <div className="App">
        <header className="App-header">
          <img src={logo} className="App-logo" alt="logo"/>
          <h1 className="App-title">Welcome to React</h1>
        </header>
        <p className="App-intro">
          To get started, edit <code>src/App.tsx</code> and save to reload.
        </p>
      </div>
    );
  }
}

export default App;
