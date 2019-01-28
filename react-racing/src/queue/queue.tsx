import * as React from 'react'
import {Dispatch} from "redux";
import {connect} from "react-redux";
import {getUserQueue} from "./queue.actions";
import {User} from "../backend-event-channel/user";
import {ActionType} from "typesafe-actions";

export interface UserQueueStateProps {
    userQueue: User[];
    onGetUserQueue: (resp: User[]) => void;
    currentUser: User;
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
                    {!this.isRegistered() ? <button className="btn btn-success mb-3" onClick={this.registerForRace}>Anmäl mig</button> :
                        <button className="btn btn-danger mb-3" onClick={this.unRegisterForRace}>Fega ur</button> }

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
        fetch('http://localhost:10580/userqueue')
            .then(response => response.json())
            .then(resp => {
                this.props.onGetUserQueue(resp)
            });
    }

    registerForRace = () => {
        fetch('http://localhost:10580/userqueue', {
            method: 'POST',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                id: this.props.currentUser.userId,
                timestamp: 'timestamp',
                userId: this.props.currentUser.userId,
                displayName: this.props.currentUser.displayName
            })
        }).then(() => this.loadUserQueue())

    }

    unRegisterForRace = () => {
        fetch('http://localhost:10580/userqueue', {
            method: 'DELETE',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(this.props.currentUser)
        }).then(() => this.loadUserQueue())

    }

    isRegistered() {
        return (this.props.userQueue || []).filter(user => user.userId === this.props.currentUser.userId).length === 1;
    }
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
)(Queue)
