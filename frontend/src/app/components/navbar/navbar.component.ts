import {Component, OnDestroy, OnInit, ViewChild} from '@angular/core';
import {LoginService} from "../../login/login.service";
import {ModalComponent} from "../modal/modal.component";
import {ModalConfig} from "../modal/modal.config";
import {LoginComponent} from "../../login/login.component";
import {routes} from "../../core/const/consts";
import {SubscriptionContainer} from "../../core/utils/subscription-container";
import {LoggedDto} from "../../core/models/login.models";
import {Router} from "@angular/router";
import {DiscoverService} from "../../pages/discover/discover.service";

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss']
})
export class NavbarComponent implements OnInit, OnDestroy {

  isLogged: boolean;
  userName: string = '';
  subscriptions = new SubscriptionContainer();
  modalConfig: ModalConfig = this.login;
  isNavbarCollapsed = true;

  followedRef = routes.followed;
  discoverRef = routes.discover;
  profileRef = routes.myProfile;
  messagesRef = routes.messages;
  registerRef = routes.register;
  myMeetingsRef = routes.myMeetings;
  homeRef = routes.home;

  @ViewChild('modal') private modalComponent: ModalComponent;

  constructor(private service: LoginService,
              private router: Router,
              private discoverService: DiscoverService,
              private login: LoginComponent) { //TODO: i tak też nie
    this.isLogged = localStorage.getItem("isLogged") === 'true';
  }

  async openModal() {
    return await this.modalComponent.open()
  }

  ngOnInit(): void {
    this.subscriptions.add = this.service.onLogin$.subscribe($event => {
      this.isLogged = $event;
      this.subscriptions.add = this.service.doGetIsLogged().subscribe(data => {
        this.setUserInfo(data);
      });
    });
    this.subscriptions.add = this.service.doGetIsLogged().subscribe(data => {
      this.setUserInfo(data);
    });


  }

  private setUserInfo(data: LoggedDto) {
    if (data) {
      this.userName = data.userName;
      this.isLogged = data.active && data.userName.length > 0;
    } else {
      this.isLogged = false;
      this.service.logout();
    }
  }

  logout() {
    this.service.logout();
  }

  ngOnDestroy(): void {
    this.subscriptions.dispose();
  }

  searchDiscover(searchInput: HTMLInputElement) {
    this.discoverService.searchReq.next(searchInput.value);
    searchInput.value="";
    this.router.navigateByUrl(routes.discover);
  }
}
