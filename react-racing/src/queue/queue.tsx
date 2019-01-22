import * as React from 'react'
import {Dispatch} from "redux";
import {ActionType} from "typesafe-actions";
import {connect} from "react-redux";
import {getUserQueue} from "./queue.actions";

export interface User {
    userId: string
    displayName: string
    password: string

}

export interface UserQueueStateProps {
    userQueue: User[];
    onGetUserQueue: (resp: User[]) => void;
}

export class Queue extends React.Component<UserQueueStateProps> {

    componentDidMount(): void {
        this.loadUserQueue()
    }

    render() {
        return (
            <div className="container">
                <h1>Queue to next race</h1>
                <div className="margin-bottom-sm">
                    <button className="btn btn-success mb-3">Anmäl mig</button>
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
}

function mapStateToProps(state: any) {
    return {
        userQueue: state.userQueueState.userQueue
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
