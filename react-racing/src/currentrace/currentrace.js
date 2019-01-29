import * as React from "react"
import { BackendEventChannelState } from "../backend-event-channel/backend-event-channel.state";
import { connect } from "react-redux";
import { Dispatch } from "redux";
import { AppContextConsumer } from "../index";
import { getCurrentrace } from "./currentrace.action"

export class Currentrace extends React.Component {

  componentDidMount = () => {
    
    this.props.context.store.subscribe(() => {
        this.props.context.clientApi.fetchCurrentrace().then((resp) => {
            this.props.onGetCurrentrace(resp);
        })
    })
  };

  shouldComponentUpdate = () => {
        return false;
    }
  
  render() {
    return (
      <div className="container">
        hej
      </div>
    )
  }
}

function AppContextWithCurrentrace(state) {
    return (
      <AppContextConsumer>
        {clientApi =>
          <Currentrace context={clientApi} {...state} />
        }
      </AppContextConsumer>
    )
}

function mapStateToProps(state) {
    return {
      backendEventChannelState: state.backendEventChannelState,
      currentrace: state.currentraceState.currentrace
    };
  }
  
  function mapDispatchToProps(dispatch) {
    return {
      onGetCurrentrace: (resp) => dispatch(getCurrentrace(resp))
    };
  }

export default connect(
    mapStateToProps,
    mapDispatchToProps
)(AppContextWithCurrentrace)

