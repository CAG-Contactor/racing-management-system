///<reference path="./_references.d.ts"/>
import 'bootstrap';
import 'bootstrap/css/bootstrap.css!';
import 'zone'; // See https://gist.github.com/robwormald/429e01c6d802767441ec
import 'reflect-metadata';

import {
  Component,
  View,
  NgZone,
  bootstrap,
  FormBuilder,
  ControlGroup,
  AbstractControl,
  FORM_DIRECTIVES,
  NgIf
} from "angular2/angular2";
import {
  HTTP_PROVIDERS,
  Http,
  Response,
  RequestOptions,
  RequestMethods
} from "angular2/http";
import {NgClass} from "../jspm_packages/npm/angular2@2.0.0-alpha.44/ts/src/core/directives/ng_class";

@Component({
  selector: 'myapp',
  providers: [HTTP_PROVIDERS]
})
@View({
  directives: [FORM_DIRECTIVES, NgIf],
  template: `
<div class="container">
  <h1>CAG Detektor-simulator</h1>
  <form [ng-form-model]="mainForm">
      <div class="form-group">
        <label for="raceServer"></label>
        <input type="url"
            class="form-control"
            id="raceServer"
            placeholder="Server för Aktuellt Lopp (<värdnamn/IP-adress>:<port>)"
            [ng-form-control]="mainForm.controls['serverAddress']"
            >
      </div>
  </form>
  <button type="button" class="btn btn-danger" (click)="passage('START')">Start</button>
  <button type="button" class="btn btn-warning" (click)="passage('SPLIT')">Mellan</button>
  <button type="button" class="btn btn-success" (click)="passage('FINISH')">Mål</button>
  <hr>
  <dl class="dl-horizontal">
      <dt>t<sub>start</sub></dt>
      <dd>{{tStart}}</dd>
      <dt>t<sub>mellan</sub></dt>
      <dd>{{tSplit}} (&Delta;t<sub>mellan-start</sub> {{tSplit > tStart ? (tSplit - tStart)/1000.0 : '--'}} s)</dd>
      <dt>t<sub>mål</sub></dt>
      <dd>{{tFinish}} (&Delta;t<sub>mål-start</sub> {{tFinish > tStart && tFinish > tSplit? (tFinish - tStart)/1000.0 : '--'}} s)</dd>
  </dl>
  <div *ng-if="errorMessage">
    <br>
    <div class="alert alert-danger" role="alert">{{errorMessage}}</div>
  </div>
</div>
`
})
export class Main {
  private errorMessage:string;
  private http:Http;
  private mainForm:ControlGroup;
  private serverAddress:string;
  private seq:number = 0;
  private tStart:number = 0;
  private tSplit:number = 0;
  private tFinish:number = 0;

  constructor(http:Http, fb:FormBuilder) {
    this.http = http;
    this.serverAddress = getFromLocalStorage('serverAddress', 'localhost:12080');
    this.mainForm = fb.group({
      'serverAddress': [this.serverAddress]
    });
    this.mainForm.controls['serverAddress'].valueChanges.subscribe((value)=> {
      console.debug('Server Address:', value);
      this.serverAddress = value;
      putToLocalStorage('serverAddress', this.serverAddress);
    });
  }

  passage(sensorId:string) {
    var time = new Date().getTime();
    switch (sensorId) {
      case 'START':
        this.tStart = time;
        break;
      case 'SPLIT':
        this.tSplit = time;
        break;
      case 'FINISH':
        this.tFinish = time;
        break;

    }
    this.errorMessage = undefined;
    this.seq++;

    const data = JSON.stringify({
      sensorID: sensorId,
      timestamp: time
    });
    this.http.post('http://' + this.serverAddress + '/passageDetected?sensorID=' + sensorId + '&timestamp=' + time)
      .subscribe(
        response => {
          console.debug('res:', response);
        },
        err => {
          console.warn('err:', err);
          this.errorMessage = 'Oops, nåt gick åt skogen när passagedata skulle skickas!';
        },
        () => console.debug('XHR request completed')
      );
    console.debug(sensorId);

  }
}

function putToLocalStorage(key:string, value:any) {
  if (typeof(Storage) !== "undefined") {
    localStorage.setItem(key, JSON.stringify(value));
  } else {
    // Sorry! No Web Storage support..
  }
}

function getFromLocalStorage(key:string, defaultValue:any) {
  if (typeof(Storage) !== "undefined") {
    let fetched = localStorage.getItem(key);
    return fetched ? JSON.parse(fetched) : defaultValue;
  }
  return defaultValue;
}

bootstrap(Main, [HTTP_PROVIDERS]);

console.log('Loaded');
