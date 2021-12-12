import {Component, OnDestroy, OnInit} from '@angular/core';
import {DiscoverService} from "./discover.service";
import {DiscoverDto} from "../../core/models/discover.models";
import {Subscription} from "rxjs";
import {DomSanitizer} from "@angular/platform-browser";
import {routes} from "../../core/const/consts";
import {ProfileService} from "../profile/profile.service";

@Component({
  selector: 'app-discover',
  templateUrl: './discover.component.html',
  styleUrls: ['./discover.component.scss']
})
export class DiscoverComponent implements OnInit, OnDestroy {

  profiles: DiscoverDto[];
  subscriptions: Subscription[] = [];
  currentRate = 0;
  routeToProfile = routes.profile+'/';

  constructor(private service: DiscoverService,
              private domSanitizer: DomSanitizer,
              private profileService: ProfileService) {
  }

  ngOnInit(): void {
    this.subscriptions.push(this.service.getList().subscribe(data => {
      this.profiles = data.content;
      this.profiles.forEach(x => x.city = this.firstLetterUpper(x.city))
    }));
  }

  firstLetterUpper(str: string) {
    return this.profileService.firstLetterUpper(str);
  }

  showPic(url: string) {
    return this.profileService.showPic(url);
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach(sub=>sub.unsubscribe());
  }
}
