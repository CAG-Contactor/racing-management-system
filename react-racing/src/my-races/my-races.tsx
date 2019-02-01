import * as React from "react"
import { UserResult } from "../leaderboard/Leaderboard";
import { BackendEventChannelState } from "../backend-event-channel/backend-event-channel.state";
import Moment from "react-moment";
import { connect } from "react-redux";
import { ActionType } from "typesafe-actions";
import { Dispatch } from "redux";
import { getMyRaces } from "./my-races.actions";
import { User } from "../backend-event-channel/user";
import { AppContextConsumer, IAppContext } from "../index";

export interface MyRacesStateProps {
  backendEventChannelState: BackendEventChannelState;
  myRaces: UserResult[];
  onGetMyRaces: (resp: UserResult[]) => void;
  currentUser: User
  context: IAppContext
}

export class MyRaces extends React.Component<MyRacesStateProps> {

  componentDidMount = () => {
    this.fetchMyRaces(this.props.currentUser.userId);
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

  render() {
    if (this.props.backendEventChannelState.lastReceivedEvent) {
      const backendEvent: BackendEvent = this.props.backendEventChannelState.lastReceivedEvent;

      if (this.isNewResult(backendEvent)) {
        this.fetchMyRaces(this.props.currentUser.userId);
      }
    }

    const myValidRaces = this.props.myRaces.filter(x => x.result === 'FINISHED');

    return (
      <div style={{  fontSize: 12}} className="container">
        <div className="row">
        <h2>Mina Lopp</h2>
        {myValidRaces.length === 0 && 'Det finns inga godkända resultat än...'}
        {myValidRaces.length > 0 &&
        <table className="center table table-striped">
          <thead>
          <tr>
            <th className="col-xs-2">Namn</th>
            <th className="col-xs-2">Tid</th>
            <th className="col-xs-2">Mellantid</th>
            <th className="col-xs-2">Resultat</th>
          </tr>
          </thead>
          <tbody>
          {myValidRaces.map((myRace: UserResult, index: number) => {
            return (
              <tr key={index}>
                <td className="col-xs-2">{myRace.user.displayName}</td>
                <td className="col-xs-2"><Moment format="mm:ss:SSS">{myRace.time}</Moment></td>
                <td className="col-xs-2"><Moment format="mm:ss:SSS">{myRace.splitTime}</Moment></td>
                <td className="col-xs-2">{this.getResultText(myRace.result)}</td>
              </tr>
            );
          })}
          </tbody>
        </table>
        }
        </div>
      </div>
    )
  }

  private fetchMyRaces(userId: string) {
    this.props.context.clientApi.fetchMyRaces(userId)
      .then((myRaces: UserResult[]) => {
        this.props.onGetMyRaces(myRaces);
      })
  }

  private isNewResult = (backendEvent: BackendEvent): boolean => {
    if (backendEvent.eventType !== 'NEW_RESULT') {
      return false
    }

    const existingResult = this.props.myRaces.find((result) => {
      return result.created === backendEvent.data.created;
    });

    return !existingResult;
  };
}

function AppContextWithMyRaces(state: any) {
  return (
    <AppContextConsumer>
      {clientApi =>
        <MyRaces context={clientApi} {...state} />
      }
    </AppContextConsumer>
  )
}

function mapStateToProps(state: any) {
  return {
    backendEventChannelState: state.backendEventChannelState,
    myRaces: state.myRacesState.myRaces,
    currentUser: state.appState.user
  };
}

function mapDispatchToProps(dispatch: Dispatch<ActionType<typeof getMyRaces>>) {
  return {
    onGetMyRaces: (myRaces: UserResult[]) => dispatch(getMyRaces(myRaces))
  };
}

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(AppContextWithMyRaces)

