import {Component, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {ProfileService} from "./profile.service";
import {Subscription} from "rxjs";
import {ProfileDto} from "../../core/models/profile.models";
import {DatePipe} from "@angular/common";

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.scss'],
  providers: [DatePipe]
})
export class ProfileComponent implements OnInit, OnDestroy {

  subscriptions: Subscription[] = [];
  profile: ProfileDto;

  constructor(private route: ActivatedRoute,
              private service: ProfileService,
              private datePipe: DatePipe) {
  }

  ngOnInit(): void {
    let username = this.route.snapshot.paramMap.get('username');
    this.subscriptions.push(this.service.getProfileDetails(username).subscribe(data => {
      this.profile = data;
    }));
  }

  formatDate(date: Date) {
    return this.datePipe.transform(date, 'dd.MM.yyyy HH:mm');
  }

  showPic(url: string) {
      return this.service.showPic(url);
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach(sub => sub.unsubscribe());
  }
}
