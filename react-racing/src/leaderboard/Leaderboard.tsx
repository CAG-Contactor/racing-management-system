import * as React from 'react'
import { connect } from "react-redux"
import Moment from 'react-moment';
import { BackendEventChannelState } from '../backend-event-channel/backend-event-channel.state'
import { getLeaderboard } from "./leaderboard.actions";
import { Dispatch } from 'redux';
import { ActionType } from 'typesafe-actions';
import { AppContextConsumer, IAppContext } from 'src';

export interface LeaderboardStateProps {
  backendEventChannelState: BackendEventChannelState;
  leaderboard: UserResult[];
  onGetLeaderboard: (resp: UserResult[]) => void;
  context: IAppContext;
}

export interface UserResult {
  created: number;
  result: string;
  splitTime: number;
  time: number;
  user: User;
}

export interface User {
  displayName: string
}

class Leaderboard extends React.Component<LeaderboardStateProps> {
  
  componentDidMount = () => {
    this.fetchLeaderboard()
  };

  getResultText = (type: string) => {
    switch (type) {
      case 'FINISHED':
        return 'Godkänd';
      case 'WALKOVER':
        return 'Walkover';
      default:
        return 'Diskad'
    }
  };

  fetchLeaderboard = () => {
      this.props.context.clientApi.fetchLeaderboard().then((resp: UserResult[]) => {
        this.props.onGetLeaderboard(resp)
      })
      
  }
    
  isNewResult = (backendEvent: BackendEvent): boolean => {
    if (backendEvent.eventType !== 'NEW_RESULT') {
      return false
    }

    const existingResult = this.props.leaderboard.find((x) => {
      return x.created === backendEvent.data.created;
    });

    return !existingResult;
  };

  render() {
    if (this.props.backendEventChannelState.lastReceivedEvent) {
      const backendEvent: BackendEvent = this.props.backendEventChannelState.lastReceivedEvent as BackendEvent

      if (this.isNewResult(backendEvent)) {
        this.fetchLeaderboard()
      }
    }

    let position = 1;

    return (
      <div>
        <h2>Resultattavla</h2>
        {this.props.leaderboard.length === 0 && 'Det finns inga resultat än...'}
        {this.props.leaderboard.length > 0 &&
        <table className="center table table-striped">
          <thead>
          <tr>
            <th>Plats</th>
            <th>Namn</th>
            <th>Tid</th>
            <th>Mellantid</th>
            <th>Resultat</th>
          </tr>
          </thead>
          <tbody>
          {this.props.leaderboard.map((userResult: UserResult, index: number) => {
            return (
              <tr key={index}>
                <td>{position++}</td>
                <td>{userResult.user.displayName}</td>
                <td><Moment format="mm:ss:SSS">{userResult.time}</Moment></td>
                <td><Moment format="mm:ss:SSS">{userResult.splitTime}</Moment></td>
                <td>{this.getResultText(userResult.result)}</td>
              </tr>
            );
          })}
          </tbody>
        </table>
        }
      </div>
    )
  }
}

function AppContextWithLeaderboard(state: any) {
  return (
    <AppContextConsumer>
      {clientApi =>
        <Leaderboard context={clientApi} {...state} />
      }
  </AppContextConsumer>
  )
}

function mapStateToProps(state: any) {
  return {
    backendEventChannelState: state.backendEventChannelState,
    leaderboard: state.leaderboardState.leaderboard
  };
}

function mapDispatchToProps(dispatch: Dispatch<ActionType<typeof getLeaderboard>>) {
  return {
    onGetLeaderboard: (leaderboard: UserResult[]) => dispatch(getLeaderboard(leaderboard))
  };
}

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(AppContextWithLeaderboard)
