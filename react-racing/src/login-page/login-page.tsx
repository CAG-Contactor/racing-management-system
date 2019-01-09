import * as React from 'react';

export interface LoginPageProps {
  loginStatusChanged: (loggedIn: boolean) => void
}

export const LoginPage = (props: LoginPageProps) => {
  return (
    <div>
      <h1>Här e en loginsida</h1>
      <button className="btn btn-default" onClick={() => props.loginStatusChanged(true)}>Logga in</button>
    </div>
  )
};