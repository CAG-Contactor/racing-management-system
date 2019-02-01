import * as React from 'react';
import {
  NavBar,
  NavBarSelections
} from '../nav-bar/nav-bar';
import { BackendEventChannel } from '../backend-event-channel/backend-event-channel';
import Queue from '../queue/queue';
import MyRaces from '../my-races/my-races';
import Leaderboard from '../leaderboard/Leaderboard';
import { Overview } from "../overview/overview";
import CurrentRace from '../currentrace/currentrace';
import { connect } from "react-redux"
import { AppContextConsumer, IAppContext } from 'src';
import { getCurrentStatus, setUser } from './main-page-action'
import { BackendEventChannelState } from '../backend-event-channel/backend-event-channel.state'
import { Animated } from "react-animated-css";


interface MainPageProps {
  websocket?: BackendEventChannel;
  onGetCurrentStatus: (resp: any) => void;
  onSetUser: (resp: any, event: any) => void;
  startMessage: string;
  backendEventChannelState: BackendEventChannelState;
  context: IAppContext;

}

interface MainPageState {
  viewSelection: NavBarSelections;
}

class MainPage extends React.Component<any, MainPageState> {
  state: MainPageState = {
    viewSelection: 'Overview'
  };

  constructor(props: MainPageProps) {
    super(props);
  }
  
  componentDidMount() {
    if (this.props.context) {
      this.props.context.clientApi.fetchRaceStatus().then((response: any) => {
         this.props.onGetCurrentStatus(response.user, response.event)
      });  
    }
  }


  getStartMessage = (): any => {
    const user = this.props.user
    const event = this.props.event
    const isSameUser = user && user.userId === this.props.currentUser.userId

    if (!isSameUser || event !== 'NONE') {
      return null
    }
      

    return (
      <Animated animationIn="flash" animationOut="fadeOut" isVisible={true}>
        <div style={{color: 'white', fontSize: 25, backgroundColor: '#359a29', borderColor: 'black'}} className="alert alert-danger">
          <div>Time to go to start!</div>
        </div>
      </Animated>
    )
  }

  public render() {
    if (this.props.backendEventChannelState && this.props.backendEventChannelState.lastReceivedEvent) {
      const backendEvent: BackendEvent = this.props.backendEventChannelState.lastReceivedEvent;
      if (backendEvent.eventType === 'CURRENT_RACE_STATUS') {
        this.props.onSetUser(backendEvent.data.user, backendEvent.data.event)
      }
       
    }

    return (
      <div>
        <NavBar currentSelection={this.state.viewSelection} onChangedSelection={this.changeSelectedView}/>
        {this.getStartMessage()} 
        <main style={{display: 'contents'}} role="main" className="inner main-content">
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
    case 'Overview':
      return <Overview/>;
    case 'Queue':
      return <Queue/>;
    case 'MyRaces':
      return <MyRaces/>;
    case 'Leaderboard':
      return <Leaderboard/>;
    case 'CurrentRace':
      return <CurrentRace/>;
    default:
      return <div>Unknown menu choice</div>;
  }
}

function mapStateToProps(state: any) {
  return {
    backendEventChannelState: state.backendEventChannelState,
    currentStatus: state.mainPageState.currentStatus,
    currentUser: state.appState.user,
    user: state.mainPageState.user,
    event: state.mainPageState.event
  };
}

function mapDispatchToProps(dispatch: any) {
  return {
    onGetCurrentStatus: (response: any, event: any) => dispatch(getCurrentStatus(response, event)),
    onSetUser: (response: any, event: any) => dispatch(setUser(response, event))
  };
}

function AppContextWithMainPage(state: any) {
  return (
    <AppContextConsumer>
      {clientApi =>
        <MainPage context={clientApi} {...state} />
      }
  </AppContextConsumer>
  )
}

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(AppContextWithMainPage)