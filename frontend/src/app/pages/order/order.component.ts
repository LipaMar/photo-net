import { Component, OnInit } from '@angular/core';
import {OrderService} from "./order.service";
import {Router} from "@angular/router";
import {routes} from "../../core/const/consts";
import {DatePipe} from "@angular/common";
import {ProfileService} from "../profile/profile.service";
import {SubscriptionContainer} from "../../core/utils/subscription-container";
import {BookMeetingDto} from "../../core/models/profile.models";
import {ScheduleService} from "../../services/schedule.service";
import {DatePattern} from "../../core/enums/datePattern";

@Component({
  selector: 'app-order',
  templateUrl: './order.component.html',
  styleUrls: ['./order.component.scss']
})
export class OrderComponent implements OnInit {

  meetingId: number;
  date: Date;
  dateToDisplay: string | null;
  hour: string;
  client: string;
  photographer: string;
  price: number;
  place: string;

  subscriptions = new SubscriptionContainer();

  constructor(private orderService: OrderService,
              private profileService: ProfileService,
              private scheduleService: ScheduleService,
              private datePipe: DatePipe,
              private router: Router) {
    if(!this.orderService.isPopulated()){
      this.router.navigateByUrl(routes.home);
    }
    this.client = orderService.client;
    this.photographer = orderService.photographer;
    this.meetingId = orderService.meetingId;
  }

  ngOnInit(): void {
    this.subscriptions.add = this.scheduleService.getMeetingInfo(this.meetingId).subscribe(meeting => {
      this.date = new Date(meeting.date);
      this.hour = meeting.timeStart;
      this.dateToDisplay = this.datePipe.transform(this.date, 'EEEE, d MMMM y', undefined, 'pl-PL');
    });
    this.subscriptions.add = this.profileService.getProfileDetails(this.photographer).subscribe(photographer => {
      this.price = photographer.price;
      this.place = photographer.city;
    });
  }

  onClickAccept(){
    const date = this.datePipe.transform(this.date, DatePattern.DATE_WITH_DASH);
    if(date){
      const meeting: BookMeetingDto = {id: this.meetingId,
        date: date,
        timeStart: this.hour,
        price: this.price,
        photographer: this.photographer};
      this.subscriptions.add = this.orderService.bookAMeeting(meeting).subscribe();
      this.router.navigateByUrl(routes.myMeetings);
    }
  }

}
