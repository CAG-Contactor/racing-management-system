import * as React from 'react';
import {
  NavBar,
  NavBarSelections
} from '../nav-bar/nav-bar';
import { BackendEventChannel } from '../backend-event-channel/backend-event-channel';
import Queue from '../queue';
import { MyRaces } from '../myraces/my-races';

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
    default:
      return <div>Okänt menyval</div>;
  }
}
