import * as React from 'react'
import { connect } from "react-redux"
import { getCurrentrace } from './currentrace.actions'


class Currentrace extends React.Component {
    
    componentDidMount() {
        console.log(this.props.backendEventChannelState.lastReceivedEvent)
        this.props.onGetCurrentrace(this.props.backendEventChannelState.lastReceivedEvent)
    }
    
    render() {
                  
        return(
            <div>fjsdifsd
                {this.props.currentrace.length}
            </div>
        )
    }
    
}

function mapStateToProps(state) {
    return {
      backendEventChannelState: state.backendEventChannelState,
      currentrace: state.currentraceState.currentrace
    };
  }
  
  function mapDispatchToProps(dispatch) {
    return {
      onGetCurrentrace: (data) => dispatch(getCurrentrace(data))
    };
  }
  
  export default connect(
    mapStateToProps,
    mapDispatchToProps
  )(Currentrace)