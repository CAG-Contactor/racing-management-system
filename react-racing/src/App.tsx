import * as React from 'react';
import { connect } from 'react-redux';
import './App.css';
import { BackendEventChannel } from './backend-event-channel/backend-event-channel';
import { LoginPage } from './login-page/login-page';
import { MainPage } from './main-page/main-page';
import { RootState } from './state';
import { appActionCreators } from './App.state';

// --- Component
interface AppProps {
  websocket: BackendEventChannel;
  isLoggedIn: boolean;
  changeLoginStatus: (loggedIn: boolean) => void
}

const App = (props: AppProps) => {
  const {isLoggedIn, websocket, changeLoginStatus = () => undefined} = props;
  return (
    <div>
      {isLoggedIn ? <MainPage websocket={websocket}/> : <LoginPage loginStatusChanged={changeLoginStatus}/>}
    </div>
  );
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
