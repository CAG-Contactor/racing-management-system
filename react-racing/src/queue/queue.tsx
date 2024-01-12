import * as React from 'react'
import {FC} from 'react'
import {Dispatch} from 'redux'
import {connect} from 'react-redux'
import {getUserQueue} from './queue.actions'
import {User} from '../backend-event-channel/user'
import {ActionType} from 'typesafe-actions'
import {AppContextConsumer, IAppContext} from '../index'
import {BackendEventChannelState} from '../backend-event-channel/backend-event-channel.state'

export interface UserQueueStateProps {
  backendEventChannelState: BackendEventChannelState;
  userQueue: User[];
  onGetUserQueue: (resp: User[], uuid: any) => void;
  currentUser: User;
  context: IAppContext;
  uuid: string;
}

export const QueueNew: FC<UserQueueStateProps> = (props) => {
  const {
    context,
    backendEventChannelState,
    uuid,
    userQueue,
    currentUser,
    onGetUserQueue
  } = props;

  if (backendEventChannelState.lastReceivedEvent
    && backendEventChannelState.lastReceivedEvent.eventType === 'QUEUE_UPDATED'
    && uuid !== backendEventChannelState.lastReceivedEvent.uuid) {
    loadUserQueue(backendEventChannelState.lastReceivedEvent.uuid)
  }

  let position = 1

  return (
    <div className="container">
      <h1>Queue to next race</h1>
      <div className="margin-bottom-sm">
        {!isRegistered() ?
          <button disabled={userQueue[0] && userQueue[0].userId === currentUser.userId}
                  className="btn btn-success mb-3" onClick={registerForRace}>Register to queue</button> :
          <button className="btn btn-danger mb-3" onClick={unRegisterForRace}>Chicken out</button>}

      </div>
      <table className="center table table-striped">
        <thead>
        <tr>
          <th>Queue Number</th>
          <th>Name</th>
        </tr>
        </thead>
        <tbody>

        {userQueue.map(user =>
          <tr key={user.userId}>
            <td>{position++}</td>
            <td>{user.displayName}</td>
          </tr>
        )}
        </tbody>
      </table>
    </div>
  )


  function loadUserQueue(pUuid?: string) {
    context.clientApi.loadUserQueue()
      .then((users: User[]) => {
        onGetUserQueue(users, pUuid)
      })
  }

  function registerForRace() {
    context.clientApi.registerForRace(currentUser)
      .then(() => loadUserQueue())
  }

  function unRegisterForRace() {
    context.clientApi.unregisterForRace(currentUser)
      .then(() => loadUserQueue())
  }

  function isRegistered() {
    return (userQueue || []).filter(user => user.userId === currentUser.userId).length === 1
  }

}

export class Queue extends React.Component<UserQueueStateProps, {}> {

  componentDidMount(): void {
    this.loadUserQueue()
  }

  render() {
    if (this.props.backendEventChannelState.lastReceivedEvent
      && this.props.backendEventChannelState.lastReceivedEvent.eventType === 'QUEUE_UPDATED'
      && this.props.uuid !== this.props.backendEventChannelState.lastReceivedEvent.uuid) {
      const uuid = this.props.backendEventChannelState.lastReceivedEvent.uuid

      this.loadUserQueue(uuid)
    }

    let position = 1

    return (
      <div className="container">
        <h1>Queue to next race</h1>
        <div className="margin-bottom-sm">
          {!this.isRegistered() ?
            <button
              disabled={this.props.userQueue[0] && this.props.userQueue[0].userId === this.props.currentUser.userId}
              className="btn btn-success mb-3" onClick={this.registerForRace}>Register to queue</button> :
            <button className="btn btn-danger mb-3" onClick={this.unRegisterForRace}>Chicken out</button>}

        </div>
        <table className="center table table-striped">
          <thead>
          <tr>
            <th>Queue Number</th>
            <th>Name</th>
          </tr>
          </thead>
          <tbody>

          {this.props.userQueue.map(user =>
            <tr key={user.userId}>
              <td>{position++}</td>
              <td>{user.displayName}</td>
            </tr>
          )}
          </tbody>
        </table>
      </div>
    )
  }

  loadUserQueue = (uuid?: any) => {
    this.props.context.clientApi.loadUserQueue()
      .then((users: User[]) => {
        this.props.onGetUserQueue(users, uuid)
      })
  }

  registerForRace = () => {
    this.props.context.clientApi.registerForRace(this.props.currentUser)
      .then(() => this.loadUserQueue())
  }

  unRegisterForRace = () => {
    this.props.context.clientApi.unregisterForRace(this.props.currentUser)
      .then(() => this.loadUserQueue())
  }

  isRegistered() {
    return (this.props.userQueue || []).filter(user => user.userId === this.props.currentUser.userId).length === 1
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
    backendEventChannelState: state.backendEventChannelState,
    userQueue: state.userQueueState.userQueue,
    currentUser: state.appState.user,
    uuid: state.userQueueState.uuid
  }
}

function mapDispatchToProps(dispatch: Dispatch<ActionType<typeof getUserQueue>>) {
  return {
    onGetUserQueue: (userQueue: User[], uuid: string) => dispatch(getUserQueue(userQueue, uuid))
  }
}

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(AppContextWithQueue)
