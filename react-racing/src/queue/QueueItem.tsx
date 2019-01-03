import * as React from 'react'


export interface QueueItemProps {
    name?: string
    id?: string

}

export default class QueueItem extends React.Component<QueueItemProps> {

    render() {
        return (<div>{this.props.name} - {this.props.id}</div>)
    }
}