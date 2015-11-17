///<reference path="./_references.d.ts"/>
import 'bootstrap';
import "bootstrap/css/bootstrap.css!"
import "reflect-metadata"

import {
    Component,
    View,
    bootstrap
} from "angular2/angular2";
import {
    HTTP_PROVIDERS,
    Http
} from "angular2/http";

@Component({
    selector: 'myapp',
    viewProviders: [HTTP_PROVIDERS]
})
@View({
    template: `
<div class="container">
  <h1>CAG Time Measurement Manager Simulator</h1>
  <button type="button" class="btn btn-danger" (click)="passageStart()">Start</button>
  <button type="button" class="btn btn-warning" (click)="passageSplit()">Mellan</button>
  <button type="button" class="btn btn-success" (click)="passageFinish()">Mål</button>
</div>
`
})
class Main {
    private http:Http;

    constructor(http:Http) {
        this.http = http;
    }

    passageStart() {
        const data = {
            sensorID: 'START',
            timestamp: new Date().getTime()
        };
        this.http.post('http://localhost:12080/passageDetected', JSON.stringify(data))
            .subscribe(res => console.debug('response', res));
        console.debug('Start');
    }

    passageSplit() {
        console.debug('Mellan');
    }

    passageFinish() {
        console.debug('Slut');
    }
}

bootstrap(Main);

console.log('Loaded');
