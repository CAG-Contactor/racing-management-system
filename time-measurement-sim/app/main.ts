///<reference path="./_references.d.ts"/>
import 'bootstrap';
import "bootstrap/css/bootstrap.css!"
import 'zone.js'; // See https://gist.github.com/robwormald/429e01c6d802767441ec
import "reflect-metadata"

import {
    Component,
    View,
    NgZone,
    bootstrap
} from "angular2/angular2";
import {
    HTTP_PROVIDERS,
    Http,
    Response
} from "angular2/http";
import {NgClass} from "../jspm_packages/npm/angular2@2.0.0-alpha.44/ts/src/core/directives/ng_class";

@Component({
    selector: 'myapp',
    providers: [HTTP_PROVIDERS]
})
@View({
    template: `
<div class="container">
  <h1>CAG Time Measurement Manager Simulator</h1>
  <button type="button" class="btn btn-danger" (click)="passage('START')">Start</button>
  <button type="button" class="btn btn-warning" (click)="passage('SPLIT')">Mellan</button>
  <button type="button" class="btn btn-success" (click)="passage('FINISH')">Mål</button>
  <hr>
  <div>Resultat {{seq}}: {{result}}</div>
</div>
`
})
export class Main {
    public result:Object;
    private http:Http;
    private seq:number = 0;

    constructor(http:Http) {
        this.http = http;
    }

    passage(sensorId:string) {
        this.result = undefined;
        this.seq++;

        const data = JSON.stringify({
            sensorID: sensorId,
            timestamp: new Date().getTime()
        });
        this.http.post(
            'http://localhost:12080/passageDetected',
            data
            )
            .subscribe(
                data => {
                    console.debug('res:', data);
                    this.result = 'Det gick bra'
                },
                err => {
                    console.warn('err:', err);
                    this.result = 'Oops, nåt gick åt skogen!';
                },
                () => console.debug('XHR request completed')
            )
        ;
        console.debug(sensorId);

    }
}

bootstrap(Main, [HTTP_PROVIDERS]);

console.log('Loaded');
