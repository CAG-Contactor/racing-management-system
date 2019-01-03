import * as React from 'react';
import './App.css';
import { BackendEventChannel } from "./backend-event-channel/backend-event-channel";
import { MyRaces } from "./myraces/MyRaces";
import Queue from './queue';
import {
  NavBar,
  NavBarSelections
} from "./nav-bar/nav-bar";

interface AppProps {
  websocket?: BackendEventChannel;
}

interface AppState {
  viewSelection: NavBarSelections;
}

class App extends React.Component<AppProps, AppState> {
  state: AppState = {
    viewSelection: 'Queue'
  };

  constructor(props: AppProps) {
    super(props);
  }

  public render() {
    return (
      <div>
        <NavBar currentSelection={this.state.viewSelection} onChangedSelection={this.changeSelectedView}/>
        <main role="main" className="inner main-content">
          {viewFor(this.state.viewSelection)}
        </main>
      </div>
    );
  }

  private changeSelectedView = (viewSelection: NavBarSelections) =>
    this.setState({viewSelection})

}

function viewFor(selection: NavBarSelections) {
  switch (selection) {
    case 'Queue':
      return <Queue/>;
    case 'MyRaces':
      return <MyRaces/>;
    default:
      return <div>Okänt menyval</div>
  }
}

export default App;
