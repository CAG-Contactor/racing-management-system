import * as React from 'react'
import { Dispatch } from "redux";
import { connect } from "react-redux";
import { getUserQueue } from "./queue.actions";
import { User } from "../backend-event-channel/user";
import { ActionType } from "typesafe-actions";
import { AppContextConsumer, IAppContext } from "../index";

export interface UserQueueStateProps {
  userQueue: User[];
  onGetUserQueue: (resp: User[]) => void;
  currentUser: User;
  context: IAppContext
}

export class Queue extends React.Component<UserQueueStateProps, {}> {

  componentDidMount(): void {
    this.loadUserQueue()
  }

  render() {

    return (
      <div className="container">
        <h1>Queue to next race</h1>
        <div className="margin-bottom-sm">
          {!this.isRegistered() ?
            <button disabled={this.props.userQueue[0] && this.props.userQueue[0].userId === this.props.currentUser.userId} className="btn btn-success mb-3" onClick={this.registerForRace}>Anmäl mig</button> :
            <button className="btn btn-danger mb-3" onClick={this.unRegisterForRace}>Fega ur</button>}

        </div>
        <table className="center table table-striped">
          <thead>
          <tr>
            <th>Name</th>
            <th>Id</th>
          </tr>
          </thead>
          <tbody>

          {this.props.userQueue.map(user =>
            <tr key={user.userId}>
              <td>{user.displayName}</td>
              <td>{user.userId}</td>
            </tr>
          )}
          </tbody>
        </table>
      </div>
    )
  }

  loadUserQueue = () => {
    this.props.context.clientApi.loadUserQueue()
      .then((users: User[]) => {
        this.props.onGetUserQueue(users)
      });
  };

  registerForRace = () => {
    this.props.context.clientApi.registerForRace(this.props.currentUser)
      .then(() => this.loadUserQueue());
  };

  unRegisterForRace = () => {
    this.props.context.clientApi.unregisterForRace(this.props.currentUser)
      .then(() => this.loadUserQueue());
  };

  isRegistered() {
    return (this.props.userQueue || []).filter(user => user.userId === this.props.currentUser.userId).length === 1;
  }
}

function AppContextWithQueue(state: any) {
  return (
    <AppContextConsumer>
      {clientApi =>
        <Queue context={clientApi} {...state} />
      }
    </AppContextConsumer>
  )
}

function mapStateToProps(state: any) {
  return {
    userQueue: state.userQueueState.userQueue,
    currentUser: state.appState.user,
  };
}

function mapDispatchToProps(dispatch: Dispatch<ActionType<typeof getUserQueue>>) {
  return {
    onGetUserQueue: (userQueue: User[]) => dispatch(getUserQueue(userQueue))
  };
}

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(AppContextWithQueue)
