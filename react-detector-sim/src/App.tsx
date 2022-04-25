import * as React from 'react';
import { ChangeEvent } from 'react';
import './App.css';

enum Sensor {
  START = 'START',
  SPLIT = 'SPLIT',
  FINISH = 'FINISH'
}

interface AppState {
  readonly tFinish: number;
  readonly tSplit: number;
  readonly tStart: number;
  readonly errorMessage?: string;
  readonly seq: number;
  readonly serverAddress: string;
}

class App extends React.Component<{}, AppState> {
  state: AppState = {
    seq: 0,
    tStart: 0,
    tSplit: 0,
    tFinish: 0,
    serverAddress: getFromLocalStorage('serverAddress', 'http://localhost:10480')
  };

  public render() {
    return (
      <div className="container">
        <h1>CAG Detektor-simulator</h1>
        <form>
          <div className="form-group">
            <input type="url"
                   className="form-control"
                   id="raceServer"
                   placeholder="Server för Aktuellt Lopp (<värdnamn/IP-adress>:<port>)"
                   value={this.state.serverAddress}
                   onChange={this.updateServerAdress}
            />
          </div>
        </form>
        <button type="button" className="btn btn-danger mr-3" onClick={this.passage(Sensor.START)}>Start</button>
        <button type="button" className="btn btn-warning mr-3" onClick={this.passage(Sensor.SPLIT)}>Mellan</button>
        <button type="button" className="btn btn-success" onClick={this.passage(Sensor.FINISH)}>Mål</button>
        <hr/>
        <dl className="dl-horizontal">
          <dt>t<sub>start</sub></dt>
          <dd>{this.state.tStart}</dd>
          <dt>t<sub>mellan</sub></dt>
          <dd>{this.state.tSplit} (&Delta;t<sub>mellan-start</sub> {this.state.tSplit > this.state.tStart ? (this.state.tSplit - this.state.tStart) / 1000.0 : '--'} s)
          </dd>
          <dt>t<sub>mål</sub></dt>
          <dd>{this.state.tFinish} (&Delta;t<sub>mål-start</sub> {this.state.tFinish > this.state.tStart && this.state.tFinish > this.state.tSplit ? (this.state.tFinish - this.state.tStart) / 1000.0 : '--'} s)
          </dd>
        </dl>
        <button type="button" className="btn btn-danger" onClick={this.resetRace}>Reset Race</button>
        {
          !!this.state.errorMessage ?
            <div>
              <br/>
              <div className="alert alert-danger" role="alert">{this.state.errorMessage}</div>
            </div>
            : ''
        }
      </div>
    );
  }

  private updateServerAdress = (inputEvent: ChangeEvent<HTMLInputElement>) => {
    const serverAddress = inputEvent.target.value;
    this.setState({serverAddress});
    putToLocalStorage('serverAddress', serverAddress);
  };

  private passage = (sensor: Sensor) => () => {
    console.debug(`Passed ${sensor}`);
    const time = new Date().getTime();
    switch (sensor) {
      case Sensor.START:
        this.setState({tStart: time});
        break;
      case Sensor.SPLIT:
        this.setState({tSplit: time});
        break;
      case Sensor.FINISH:
        this.setState({tFinish: time});
        break;

    }
    this.setState((oldState) => ({errorMessage: undefined, seq: oldState.seq + 1}));

    const data = JSON.stringify({
      sensorID: sensor,
      timestamp: time
    });

    fetch(`${this.state.serverAddress}/passageDetected?sensorID=${sensor}&timestamp=${time}`,
      {
        method: 'POST',
        body: data
      })
      .then(response => {
        if (response.ok) {
          console.debug('OK response:', response);
        }
      })
      .catch(error => {
        console.error(error)
        this.setState({errorMessage: `Oops, nåt gick åt skogen när passagedata skulle skickas! (${error})`});
      });

  };

  private resetRace = () => {
    console.debug("Reset race");
    fetch(`${this.state.serverAddress}/cancelRace`,
      {
        method: 'POST'
      })
      .then(response => {
        if (response.ok) {
          console.debug('OK response:', response);
        }
      })
      .catch(error => {
        console.error(error)
        this.setState({errorMessage: `Oops, nåt gick åt skogen när passagedata skulle skickas! (${error})`});
      });
  }
}


function putToLocalStorage(key: string, value: any) {
  if (typeof (Storage) !== "undefined") {
    localStorage.setItem(key, JSON.stringify(value));
  } else {
    // Sorry! No Web Storage support..
  }
}

function getFromLocalStorage(key: string, defaultValue: any) {
  if (typeof (Storage) !== "undefined") {
    const fetched = localStorage.getItem(key);
    return fetched ? JSON.parse(fetched) : defaultValue;
  }
  return defaultValue;
}


export default App;
