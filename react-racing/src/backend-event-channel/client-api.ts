import * as md5 from 'md5';
import { User } from './user';
import * as cookies from 'browser-cookies';
import * as fetchIntercept from 'fetch-intercept';
import { UserResult } from '../leaderboard/Leaderboard';

fetchIntercept.register({
  request: (url: string, init: RequestInit) => {
    init = init || {};
    init.headers = init.headers || {};
    if (cookies.get('x-authtoken')) {
      init.headers['x-authtoken'] = cookies.get('x-authtoken');
    }
    init.mode = 'cors';
    init.cache = 'no-cache';
    init.credentials = 'include';
    return [url, init];
  },
  response: (r: Response) => {
    if (!r.ok) {
      throw new Error(`Error in call to the clientapi: ${r.status}: ${r.statusText}`);
    } else {
      return r;
    }
  }
});

export class ClientApi {

  constructor(private readonly clientApiBaseUrl: string = 'http://localhost:10580') {
  }

  userForToken(): Promise<User> {
    // The x-authtoken is added in the interceptor, which turns this request to
    // fetch the user corresponding to the token.
    return fetch(`${this.clientApiBaseUrl}/users`)
      .then(r => r.json() as Promise<User>);
  }

  login(userName: string, password: string): Promise<User> {
    const loginCredentials = {
      userId: userName,
      password: md5(password)
    };

    return fetch(
      `${this.clientApiBaseUrl}/login`,
      {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(loginCredentials)
      })
      .then(r => {
        const authtoken = r.headers.get('x-authtoken');
        if (!!authtoken) {
          cookies.set('x-authtoken', authtoken);
        }
        return r.json();
      });
  }

  signup(userName: string, password: string): Promise<void> {
    const loginCredentials = {
      userId: userName,
      displayName: userName,
      password: md5(password)
    };

    return fetch(
      `${this.clientApiBaseUrl}/users`,
      {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(loginCredentials)
      })
      .then(() => undefined);
  }

  fetchLeaderboard(): Promise<UserResult[]> {
    return fetch(`${this.clientApiBaseUrl}/leaderboard`)
      .then(r => r.json());
  }
  
  fetchRaceStatus(): Promise<UserResult[]> {
    return fetch(`${this.clientApiBaseUrl}/currentrace`)
      .then(r => r.json());
  }

  getLastRace(): Promise<any[]> {
    return fetch(`${this.clientApiBaseUrl}/lastrace`)
      .then(r => r.json());
  }

  fetchMyRaces(userId: string): Promise<UserResult[]> {
    const user = {
      userId
    };

    return fetch(
      `${this.clientApiBaseUrl}/myraces`,
      {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(user)
      })
      .then(r => r.json());
  }

  loadUserQueue(): Promise<User[]> {
    return fetch(`${this.clientApiBaseUrl}/userqueue`)
      .then(r => r.json())
  }

  registerForRace(currentUser: User) {
    const user = {
      displayName: currentUser.displayName,
      userId: currentUser.userId,
      password: currentUser.password
    }
    return fetch(`${this.clientApiBaseUrl}/userqueue`, {
      method: 'POST',
      headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(user)
    })
  }

  unregisterForRace(currentUser: User): Promise<void> {
    return fetch(`${this.clientApiBaseUrl}/userqueue`, {
      method: 'DELETE',
      headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(currentUser)
    }).then(() => undefined)
  }
}