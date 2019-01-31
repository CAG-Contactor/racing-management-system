import * as React from 'react';
import { connect } from 'react-redux';
import './App.css';
import { BackendEventChannel } from './backend-event-channel/backend-event-channel';
import { LoginPage } from './login-page/login-page';
import AppContextWithMainPage from './main-page/main-page';
import { RootState } from './state';
import {
  appActionCreators,
  LoginStatus
} from './App.state';
import * as cookies from 'browser-cookies';


// --- Component
interface AppProps {
  websocket: BackendEventChannel;
  isLoggedIn: boolean;
  changeLoginStatus: (loginStatus: LoginStatus) => void
}

const App = (props: AppProps) => {
  const {isLoggedIn, websocket, changeLoginStatus = () => undefined} = props;
  return (
    <div>
      {isLoggedIn ?
        <div>
          <a className='btn btn-outline-secondary float-right'
            href="javascript:void 0"
             onClick={logout}>Logout</a>
          <AppContextWithMainPage websocket={websocket}/>
        </div> :
        <LoginPage loginStatusChanged={changeLoginStatus}/>
      }
    </div>
  );

  function logout() {
    cookies.erase('x-authtoken');
    changeLoginStatus({loggedIn: false})
  }
};

// --- Connect state <-> component
function mapStateToProps(state: RootState) {
  return {
    isLoggedIn: state.appState.isLoggedIn
  };
}

export default connect(
  mapStateToProps,
  {
    ...appActionCreators
  }
)(App);
