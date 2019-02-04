import * as React from 'react'
import { connect } from "react-redux"
import { BackendEventChannelState } from '../backend-event-channel/backend-event-channel.state'
import { getRaceStatus, setRunningTime, setFinishTime, setSplitTime, setUsername, getLastRace, setRaceEvent } from './currentrace.actions';
import { AppContextConsumer, IAppContext } from 'src';
import Moment from 'react-moment';

export interface CurrentraceStateProps {
    backendEventChannelState: BackendEventChannelState;
    onGetRaceStatus: (resp: any, uuid: string) => void; 
    onSetRunningTime: (runningTime: any) => void;
    onSetFinishTime: (finishTime: any) => void; 
    onSetSplitTime: (splitTime: any) => void;
    onSetUsername: (username: any) => void;
    onGetLastRace: (lastRace: any) => void;
    onSetRaceEvent: (event: any) => void;


    context: IAppContext;
    currentrace: any;
    state: string;
    event: string;
    uuid: string;
    runningTime: any;
    finishTime: any;
    splitTime: any;
    username: any;
    lastRace: any;
    raceEvent: any;
}

export interface RaceStatus {
    state: string;
    event: string;
}

export interface State {
    runningTime: string;
}

class Currentrace extends React.Component<CurrentraceStateProps> {
    isRaceActive: any;
    startTime: any;
    runningTime: any;
    tzOffset: any;
    timerHandle: any;
    finishTime: any;
    username: any;
    raceEvent: string;
    splitTime: any;
    lastRace: any;


    componentWillMount () {
        this.timer()
        this.updateLastRace()
        
    }

    timer = () => {
        const tzOffset = new Date(0).getTimezoneOffset() * 1000 * 60;

        const isRaceActive = this.props.currentrace.state === 'ACTIVE';
        if (isRaceActive && this.props.currentrace.startTime) {
            const runningTime = Date.now() - this.props.currentrace.startTime + tzOffset;
            this.props.onSetRunningTime(runningTime)
        }
        setTimeout(() => this.timer(), 100);
    }


    fetchRaceStatus = (uuid: string) => {
        this.props.context.clientApi.fetchRaceStatus().then((resp: any) => {
          this.props.onGetRaceStatus(resp, uuid)
        }) 
    }
    
    handleRaceStatusUpdate = (backendEvent: any) => {
        const tzOffset = new Date(0).getTimezoneOffset() * 1000 * 60;

            this.props.onSetRaceEvent(backendEvent.data.event)


        if (backendEvent.data.state === "ACTIVE") {
            const username = backendEvent.data.user.displayName;
            this.props.onSetUsername(username)
            switch(backendEvent.data.event) {
                case 'NONE':
                    this.props.onSetSplitTime(undefined)
                    
                    this.props.onSetRunningTime(undefined)
                    this.props.onSetFinishTime(undefined)
                    break;
                case 'START':
                    this.props.onSetSplitTime(undefined)

                    this.props.onSetFinishTime(undefined)
                    this.props.onSetRunningTime(undefined)
                    break;
                case 'SPLIT':
                    this.props.onSetSplitTime(backendEvent.data.splitTime - backendEvent.data.startTime + tzOffset)

                    break;
                case 'FINISH':
                    const finishTime = backendEvent.data.finishTime - backendEvent.data.startTime + tzOffset;
                    this.props.onSetFinishTime(finishTime)

                    setTimeout( () => this.updateLastRace(), 1000);
                    break;
                }
            } else if (backendEvent.data.splitTime && backendEvent.data.startTime && backendEvent.data.finishTime) {
                const splitTime = backendEvent.data.splitTime - backendEvent.data.startTime + tzOffset;
                this.props.onSetSplitTime(splitTime)
                
                const finishTime = backendEvent.data.finishTime - backendEvent.data.startTime + tzOffset;
                this.props.onSetFinishTime(finishTime)

                setTimeout( () => this.updateLastRace(), 1000);
            }
    }

    getTextLastRace = (event: any) => {
        switch (event) {
            case 'DISQUALIFIED':
            return 'Disqualified';
            case 'TIME_OUT_NOT_STARTED':
            return 'Walkover';
            case 'TIME_OUT_NOT_FINISHED':
            return 'Never finished race';
            default:
            return 'Finished';
        }
    }

    updateLastRace = () => {
        this.props.context.clientApi.getLastRace().then((response: any) => {
            if (response && response.user) {
                this.props.onGetLastRace(response)
            }
        })
    }

    getLastRaceView = (): any => {
        const tzOffset = new Date(0).getTimezoneOffset() * 1000 * 60;

        const lastRace = this.props.lastRace
        const finishTime = lastRace.finishTime ? lastRace.finishTime - lastRace.startTime + tzOffset : undefined
        const splitTime = lastRace.splitTime ? lastRace.splitTime - lastRace.startTime + tzOffset : undefined
        const user = lastRace.user.displayName
        const text = this.getTextLastRace(lastRace.event)

        return (
           <div>
                 <hr />
                    <div className="col-xs-12"><h3>Previous race</h3></div>
                    <br />
                    <div className="center col-xs-12">
                        <table className="center w-100">
                        <thead>
                            <tr>
                                <th className="col-xs-2 center"><strong>Contestant</strong></th>
                                <th className="col-xs-2 center"><strong>Time</strong></th>
                                <th className="col-xs-2 center"><strong>Split</strong></th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr>
                                <td className="col-xs-2 center">{user}</td>
                                <td className="col-xs-2 center">
                                
                                    { finishTime && <Moment format="mm:ss:SSS">{finishTime}</Moment> }
                                    { !finishTime && "--:--.---" }
                                </td>

                                <td className="col-xs-2 center"> 
                                    { splitTime && <Moment format="mm:ss:SSS">{splitTime}</Moment> }
                                    { !splitTime &&  "--:--.---"}
                                </td>
                            </tr>
                            </tbody>
                        </table>
                        <br/>
                        <p className="col-xs-12"><strong><em>Result: {text}</em></strong></p>
                    </div>
           </div>
        )
    }
    

    render() {
        if (this.props.backendEventChannelState.lastReceivedEvent) {
            const backendEvent: BackendEvent = this.props.backendEventChannelState.lastReceivedEvent as BackendEvent
            if (backendEvent.eventType === 'CURRENT_RACE_STATUS'
            && backendEvent.uuid !== this.props.uuid) {
                this.fetchRaceStatus(backendEvent.uuid)
                this.handleRaceStatusUpdate(backendEvent)
                
            }
        }

        
        const isRaceActive = this.props.currentrace.state === 'ACTIVE';
        const raceEvent = this.props.raceEvent
        return(
            <div className="container">
                <div className="center">
                { isRaceActive &&  <h1>Race in progress</h1> }
                { !isRaceActive && <h1 className="mb-3">No race in progress</h1> }
                </div>
                <div className="center" style={{fontSize: 20}}>
                    {(() => {
                        switch(raceEvent) {
                        case 'DISQUALIFIED':
                            return "Sucker! Disqualified!";
                        case 'NONE':
                            return "Waiting for start...";
                        case 'START':
                            return "Race is underway!";
                        case 'SPLIT':
                            return "Staging passed!";
                        case 'FINISH':
                            return "Finish line passed!";
                        default:
                            return null;
                        }
                    })()}
                </div>                
                <div className="active-race">
                    <div>
                        <span className="col-xs-6"><span className="pull-right mb-1" style={{fontSize: 25, paddingRight: 20}}>Contestant</span></span>
                        <span className="col-xs-6">
                            { this.props.username && <span style={{fontSize: 20}}>{this.props.username}</span> }
                            { !this.props.username && <span style={{fontSize: 20}}>----</span> }
                        </span>
                    </div>
                    <div>
                        <span className="col-xs-6">
                            <span className="pull-right">
                                { !this.props.finishTime && <span className="mb-1" style={{fontSize: 25, paddingRight: 20}}>Time</span>}
                                { this.props.finishTime && <span className="mb-1"style={{fontSize: 25, paddingRight: 20}}>Finish time</span>}
                            </span>
                        </span>
                        <span className="col-xs-6">
                            { this.props.runningTime && !this.props.finishTime && <span style={{fontSize: 20}}><Moment format="mm:ss:SSS">{this.props.runningTime}</Moment></span>}
                            { this.props.finishTime && <span style={{fontSize: 20}}><Moment format="mm:ss:SSS">{this.props.finishTime}</Moment></span> }
                            { !this.props.runningTime && !this.props.finishTime && <span style={{fontSize: 20}}>--:--.---</span>}
                        </span>
                    </div>
                    <div>
                        <span className="col-xs-6">
                            <span className="pull-right mb-1" style={{fontSize: 25, paddingRight: 20}}>Split</span>
                        </span>
                        <span className="col-xs-6">
                            { this.props.splitTime && <span style={{fontSize: 20}}><Moment format="mm:ss:SSS">{this.props.splitTime}</Moment></span> }
                            { !this.props.splitTime && <span style={{fontSize: 20}}>--:--.---</span> }
                        </span>
                    </div>
                </div>

                { this.props.lastRace && this.getLastRaceView() }
            </div>
        )
    }
}

function AppContextWithCurrentRace(state: any) {
    return (
      <AppContextConsumer>
        {clientApi =>
          <Currentrace context={clientApi} {...state} />
        }
    </AppContextConsumer>
    )
  }

function mapStateToProps(state: any) {
    return {
        backendEventChannelState: state.backendEventChannelState,
        currentrace: state.currentraceState.raceStatus,
        uuid: state.currentraceState.uuid,
        runningTime: state.currentraceState.runningTime,
        finishTime: state.currentraceState.finishTime,
        splitTime: state.currentraceState.splitTime,
        username: state.currentraceState.username,
        lastRace: state.currentraceState.lastRace,
        raceEvent: state.currentraceState.event
    };
  }

  function mapDispatchToProps(dispatch: any) {
    return {
        onGetRaceStatus: (raceStatus: any, uuid: string) => dispatch(getRaceStatus(raceStatus, uuid)),
        onSetRunningTime: (runningTime: any) => dispatch(setRunningTime(runningTime)),
        onSetFinishTime: (finishTime: any) => dispatch(setFinishTime(finishTime)),
        onSetSplitTime: (splitTime: any) => dispatch(setSplitTime(splitTime)),
        onSetUsername: (username: any) => dispatch(setUsername(username)),
        onGetLastRace: (lastRace: any) => dispatch(getLastRace(lastRace)),
        onSetRaceEvent: (event: any) => dispatch(setRaceEvent(event))
    };
  }

export default connect(
    mapStateToProps,
    mapDispatchToProps
)(AppContextWithCurrentRace)