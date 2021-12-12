import {Component, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {ProfileService} from "./profile.service";
import {Subscription} from "rxjs";
import {routes} from "../../core/const/consts";
import {DiscoverService} from "../discover/discover.service";
import {DomSanitizer} from "@angular/platform-browser";
import {ProfileDto} from "../../core/models/profile.models";

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.scss']
})
export class ProfileComponent implements OnInit, OnDestroy {

  subscriptions: Subscription[] = [];
  profile: ProfileDto;

  constructor(private route: ActivatedRoute,
              private service: ProfileService) {
  }

  ngOnInit(): void {
    let username = this.route.snapshot.paramMap.get('username');
    this.subscriptions.push(this.service.getProfileDetails(username).subscribe(data => {
      this.profile = data;
    }));
  }
  firstLetterUpper(str: string) {
    return this.service.firstLetterUpper(str);
  }

  showPic(url: string) {
    return this.service.showPic(url);
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach(sub=>sub.unsubscribe());
  }
}
