import * as React from 'react'
import QueueItem from "./QueueItem";


export default class Queue extends React.Component<{}, {}> {

    users = [{"userId": "y1@mail.com", "displayName": "y1@mail.com", "password": null}, {
        "userId": "dd@mail.com",
        "displayName": "dd",
        "password": null
    }]

    render() {

        loadUserQueue()

        return (
            <div className="container">
                <table className="center">
                    <thead>
                    <tr>
                        <th>Namn</th>
                        <th>Id</th>
                    </tr>
                    </thead>
                    <tbody>
                {this.users.map(user =>
                    <QueueItem key={user.userId} name={user.displayName} id={user.userId}/>
                )}
                    </tbody>
                </table>
            </div>
        )
    }
}

// Hämta users från API och lägga in i tabellen
function loadUserQueue() {
    fetch('http://localhost:10580/userqueue')
        .then(r => r.json())
        .then(j => console.log('r', j))
}
