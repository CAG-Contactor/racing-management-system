import * as React from 'react'
import QueueItem from "./QueueItem";


export default class Queue extends React.Component<{},{}> {

    users = [{name:'Kalle', id:'kalleId'}, {name:'Anders', id:'andersId'}, {name:'Olle', id:'OlleId'}]

    render() {
        return (
            <div>
                {this.users.map(user =>
                    <QueueItem key={user.id} name={user.name} id={user.id}/>
                )}
                </div>
        )
    }
}