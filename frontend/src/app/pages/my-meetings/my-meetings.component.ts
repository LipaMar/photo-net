import {Component, OnDestroy, OnInit} from '@angular/core';
import {ScheduleService} from "../../additional_services/schedule.service";
import {LoginService} from "../../login/login.service";
import {SubscriptionContainer} from "../../core/utils/subscription-container";
import {MeetingDto, MeetingStatus, ScheduleDto} from "../../core/models/profile.models";
import {forkJoin} from "rxjs";
import {animate, state, style, transition, trigger} from "@angular/animations";

const {FREE, CANCELED, NEW, ARCHIVAL, ACCEPTED} = MeetingStatus;

@Component({
  selector: 'app-my-meetings',
  templateUrl: './my-meetings.component.html',
  styleUrls: ['./my-meetings.component.scss'],
  animations: [
    trigger('detailExpand', [
      state('collapsed', style({height: '0px', minHeight: '0'})),
      state('expanded', style({height: '*'})),
      transition('expanded <=> collapsed', animate('225ms cubic-bezier(0.4, 0.0, 0.2, 1)')),
    ])]
})
export class MyMeetingsComponent implements OnInit, OnDestroy {

  statuses = MeetingStatus;
  subscriptions = new SubscriptionContainer();
  loggedUser: string;
  schedule: ScheduleDto;
  myMeetings: MeetingDto[];
  allMeetings: MeetingDto[] = [];
  meetingsToDisplay: MeetingDto[] = [];
  displayColumns = [{display: "Fotograf", val: 'owner'},
    {display: "Data", val: 'date'},
    {display: "Cena", val: 'price'},
    {display: "Godzina", val: 'timeStart'},
    {display: "Status", val: 'status'}];
  columns = ['owner', 'date', 'timeStart', 'price', 'status'];
  expandedElement: MeetingDto | null;

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
        this.allMeetings = meetings.concat(myMeetings).filter(meeting => meeting.status !== FREE);
        this.meetingsToDisplay = this.allMeetings;
        this.meetingsToDisplay.sort((a, b) => new Date(a.date) < new Date(b.date) ? 1 : -1);
        this.meetingsToDisplay.map(value => {
          value.status = this.translateStatus(value.status);
          value.timeStart = value.timeStart.slice(0, -3);
          value.price = value.price ? value.price + " zł" : "";
        });
      })
    });
  }

  ngOnDestroy(): void {
    this.subscriptions.dispose();
  }

  private translateStatus(status: MeetingStatus | string) {
    switch (status) {
      case FREE:
        return "Brak rezerwacji";
      case NEW:
        return "Do akceptacji";
      case ACCEPTED:
        return "Zaakceptowane";
      case ARCHIVAL:
        return "Archiwalne";
      case CANCELED:
        return "Odwołane";
      default:
        return "";
    }
  }

  rejectMeeting(meetingId: number) {
    this.subscriptions.add = this.scheduleService.updateMeetingStatus(meetingId, CANCELED).subscribe();
  }

  acceptMeeting(meetingId: number) {
    this.subscriptions.add = this.scheduleService.updateMeetingStatus(meetingId, ACCEPTED).subscribe();

  }

  cancelMeeting(meetingId: number) {
    this.subscriptions.add = this.scheduleService.updateMeetingStatus(meetingId, CANCELED).subscribe();

  }
}
