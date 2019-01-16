import * as React from 'react';
import { AppContextConsumer } from '../index';
import { ClientApi } from '../backend-event-channel/client-api';
import { FormEvent } from 'react';

export interface RegisterPageProps {
  onSignupCompleted: () => void
}

interface SignupPageState {
  userName: string;
  password: string
  password2: string
  error?: string;
}


export class SignupPage extends React.Component<RegisterPageProps, SignupPageState> {
  constructor(props: RegisterPageProps) {
    super(props);
  }

  readonly render = () => {
    return (
      <AppContextConsumer>
        {({clientApi}) => (
          <form className="form-signin"
                onSubmit={this.signup(clientApi)}>
            <h1 className="h3 mb-3 font-weight-normal">Sign in</h1>
            <input type="text"
                   id="input-email"
                   className="form-control mb-3"
                   placeholder="User name"
                   required={true}
                   autoFocus={true}
                   onChange={evt => this.setState({userName: evt.target.value})}/>
            <input type="password"
                   id="input-password"
                   className="form-control mb-3"
                   placeholder="Password"
                   required={true}
                   onChange={evt => this.setState({password: evt.target.value})}/>
            <input type="password"
                   id="input-password2"
                   className="form-control mb-3"
                   placeholder="Password (again)"
                   required={true}
                   onChange={evt => this.setState({password2: evt.target.value})}/>
            <button className="btn btn-lg btn-primary btn-block mb-3" type="submit">Sign up</button>
            {
              this.state && this.state.error ?
                <div className="alert alert-danger mt-3" role="alert">
                  {this.state.error}
                </div> :
                ''
            }
          </form>
        )
        }
      </AppContextConsumer>
    );
  };

  private signup = (clientApi: ClientApi) => (formEvt: FormEvent) => {
    formEvt.preventDefault();
    if (this.state.password !== this.state.password2) {
      this.setState({error: 'Passwords doesn\'t match'});
    } else {
      this.setState({error: undefined});
      clientApi.signup(this.state.userName, this.state.password)
        .then(user => this.props.onSignupCompleted())
        .catch((err: Error) => this.setState({error: `Something went terribly wrong: ${err.message}`}));
    }
  };
}