import {Component, OnDestroy, OnInit} from '@angular/core';
import {LoginService} from "../login/login.service";
import {Subscription} from "rxjs";

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss']
})
export class NavbarComponent implements OnInit, OnDestroy {

  onLogin: Subscription | undefined;
  isLogged: boolean;
  userName: string = '';
  subscriptions: Subscription[] = []

  constructor(private service: LoginService) {
    this.isLogged = localStorage.getItem("isLogged") === 'true';
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
