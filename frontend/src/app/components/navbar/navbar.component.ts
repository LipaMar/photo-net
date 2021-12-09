import {Component, OnDestroy, OnInit, ViewChild} from '@angular/core';
import {LoginService} from "../../login/login.service";
import {Subscription} from "rxjs";
import {ModalComponent} from "../modal/modal.component";
import {ModalConfig} from "../modal/modal.config";
import {LoginComponent} from "../../login/login.component";
import {NgbModalConfig} from "@ng-bootstrap/ng-bootstrap";
import {FormBuilder} from "@angular/forms";
import {ToastrService} from "ngx-toastr";

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss']
})
export class NavbarComponent implements OnInit, OnDestroy {

  isLogged: boolean;
  userName: string = '';
  subscriptions: Subscription[] = [];
  modalConfig: ModalConfig = this.login;
  isNavbarCollapsed = true;
  @ViewChild('modal') private modalComponent: ModalComponent;

  constructor(private service: LoginService,
              private login: LoginComponent) { //TODO: i tak też nie
    this.isLogged = localStorage.getItem("isLogged") === 'true';
  }

  async openModal() {
    return await this.modalComponent.open()
  }

  ngOnInit(): void {
    this.subscriptions.push(this.service.onLogin$.subscribe($event => {
        this.isLogged = $event;
      }),
      this.service.doGetIsLogged().subscribe(data => {
        if (data) {
          this.userName = data.userName;
          this.isLogged = data.active && data.userName.length > 0;
        } else {
          this.isLogged = false;
        }
      }));
  }

  logout() {
    this.service.logout();
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach(sub => {
      sub.unsubscribe();
    })
  }

}
