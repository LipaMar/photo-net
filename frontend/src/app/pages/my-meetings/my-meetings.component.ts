import {Component, OnDestroy, OnInit} from '@angular/core';
import {ScheduleService} from "../../additional_services/schedule.service";
import {LoginService} from "../../login/login.service";
import {SubscriptionContainer} from "../../core/utils/subscription-container";
import {MeetingDto, ScheduleDto} from "../../core/models/profile.models";
import {forkJoin} from "rxjs";

@Component({
  selector: 'app-my-meetings',
  templateUrl: './my-meetings.component.html',
  styleUrls: ['./my-meetings.component.scss']
})
export class MyMeetingsComponent implements OnInit, OnDestroy {

  subscriptions = new SubscriptionContainer();
  loggedUser: string;
  schedule: ScheduleDto;
  myMeetings: MeetingDto[];
  allMeetings: MeetingDto[] = [];
  meetingsToDisplay: MeetingDto[] = [];

  constructor(private scheduleService: ScheduleService,
              private loginService: LoginService) {
  }

  ngOnInit(): void {
    this.subscriptions.add = this.loginService.doGetIsLogged().subscribe(info => {
      this.loggedUser = info.userName;
      const schedule = this.scheduleService.getSchedule(this.loggedUser);
      const myMeetings = this.scheduleService.getMyMeetings();
      this.subscriptions.add = forkJoin([schedule, myMeetings]).subscribe(([schedule, myMeetings]: [ScheduleDto, MeetingDto[]]) => {
        this.schedule = schedule;
        this.myMeetings = myMeetings;
        const meetings = schedule.meetings ? schedule.meetings : [];
        this.allMeetings = meetings.concat(myMeetings);
        this.meetingsToDisplay = this.allMeetings;
      })
    });
  }

  ngOnDestroy(): void {
    this.subscriptions.dispose();
  }

  print(event: MeetingDto[]) {
    console.log(this.meetingsToDisplay);
    console.log(event);
  }
}
