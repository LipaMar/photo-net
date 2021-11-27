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
  isLogged: boolean = false;
  userName: string = '';

  constructor(private service: LoginService) {
    this.getLoggedUser();
    this.onLogin = this.service.onLogin$.subscribe($event => {
      this.isLogged = $event;
    });
  }

  ngOnInit(): void {
  }

  getLoggedUser() {
    this.service.doGetIsLogged().subscribe(data => {
      if(data){
        this.userName = data.userName;
        this.isLogged = data.active && data.userName.length > 0;
      }
    })
  }

  logout(){
    this.service.logout();
  }

  ngOnDestroy(): void {
    if( this.onLogin){
      this.onLogin.unsubscribe();
    }
  }

}
