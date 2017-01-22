import {Component} from "@angular/core";
import {Backend} from "../backend";

@Component({
  selector: 'sidebar-cmp',
  templateUrl: './sidebar.html'
})
export class SidebarComponent {
  isActive = false;
  showMenu: string = '';

  constructor(private readonly backend: Backend) {
  }

  eventCalled() {
    this.isActive = !this.isActive;
  }

  logout(): void {
    this.backend.logout();
  }

  addExpandClass(element: any) {
    if (element === this.showMenu) {
      this.showMenu = '0';
    } else {
      this.showMenu = element;
    }
  }
}
