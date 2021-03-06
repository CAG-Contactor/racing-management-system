import {Component, OnInit, ViewChild} from "@angular/core";
import {ModalDirective} from "ng2-bootstrap";
import {Backend, UserResult} from "../../shared";

@Component({
  selector: 'result-cmp',
  templateUrl: './result.component.html',
  styleUrls: ['result.component.scss']
})
export class ResultComponent implements OnInit {
  @ViewChild('childModal')
  childModal: ModalDirective;
  resultToRemove:UserResult;
  userResults: UserResult[] = [];

  constructor(private backend: Backend) {
  }

  ngOnInit() {
    this.reload();
  }

  private reload():void {
    this.backend.getUserResults()
      .then(ur => this.userResults = ur, () => this.userResults = []);
  }

  showRemoveConfirmation(result: UserResult): void {
    this.resultToRemove = result;
    this.childModal.show();
  }

  bail():void {
    this.resultToRemove = undefined;
    this.childModal.hide();
  }

  doRemove(): void {
    this.backend.removeUserResult(this.resultToRemove)
      .then(() => {
        console.debug("Remove: ", this.resultToRemove);
        this.resultToRemove = undefined;
        this.childModal.hide();
        this.reload();
      });
  }

  save():void {
    this.backend.downloadResultsCsvFile()
      .then(data => window.open(window.URL.createObjectURL(data)));
  }
}
