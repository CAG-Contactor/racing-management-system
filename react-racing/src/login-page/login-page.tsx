import * as React from 'react';
import { LoginStatus } from '../App.state';
import { SigninPage } from './signin-page';
import { SignupPage } from './signup-page';

export interface LoginPageProps {
  loginStatusChanged: (loginStatus: LoginStatus) => void
}

export interface LoginPageState {
  showSignupPage: boolean
}

export class LoginPage extends React.Component<LoginPageProps, LoginPageState> {
  constructor(props: LoginPageProps) {
    super(props);
  }

  readonly render = () => {
    return (
      <div>
        {this.state && this.state.showSignupPage ?
          <SignupPage onSignupCompleted={this.showSigninPage}/> :
          <SigninPage loginStatusChanged={this.props.loginStatusChanged} doSignup={this.showSignupPage}/>
        }
      </div>
    );
  };

  private readonly showSigninPage = () => {
    this.setState({showSignupPage: false});
  };

  private readonly showSignupPage = () => {
    this.setState({showSignupPage: true});
  };
}
