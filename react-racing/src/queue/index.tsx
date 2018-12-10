import * as React from 'react'
import QueueItem from "./QueueItem";


export default class Queue extends React.Component<{},{}> {

    users = [{"userId":"y1@mail.com","displayName":"y1@mail.com","password":null},{"userId":"dd@mail.com","displayName":"dd","password":null}]

    render() {
        return (
            <div>
                {this.users.map(user =>
                    <QueueItem key={user.userId} name={user.displayName} id={user.userId}/>
                )}
                </div>
        )
    }
}