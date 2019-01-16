import * as React from 'react';
import {
  NavBar,
  NavBarSelections
} from '../nav-bar/nav-bar';
import { BackendEventChannel } from '../backend-event-channel/backend-event-channel';
import Queue from '../queue/queue';
import MyRaces from '../my-races/my-races';
import Leaderboard from '../leaderboard/Leaderboard';

interface MainPageProps {
  websocket?: BackendEventChannel;
}

interface MainPageState {
  viewSelection: NavBarSelections;
}

export class MainPage extends React.Component<MainPageProps, MainPageState> {
  state: MainPageState = {
    viewSelection: 'Queue'
  };

  constructor(props: MainPageProps) {
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
    this.setState({viewSelection});

}

function viewFor(selection: NavBarSelections) {
  switch (selection) {
    case 'Queue':
      return <Queue/>;
    case 'MyRaces':
      return <MyRaces/>;
    case 'Leaderboard':
      return <Leaderboard/>;
    default:
      return <div>Unknown menu choice</div>;
  }
}
