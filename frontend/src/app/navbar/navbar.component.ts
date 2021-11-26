import {Component, OnInit} from '@angular/core';
import {LoginService} from "../login/login.service";

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss']
})
export class NavbarComponent implements OnInit {

  isLogged: boolean = false;
  userName: string = '';

  constructor(private service: LoginService) {
    this.getLoggedUser();
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

}
