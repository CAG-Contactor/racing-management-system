import * as React from 'react'


export interface QueueItemProps {
    name?: string
    id?: string

}

export default class QueueItem extends React.Component<QueueItemProps> {
    render() {
        return (
            <tr key={this.props.id}>
                <td>{this.props.name}</td>
                <td>{this.props.id}</td>
            </tr>)
    }
}