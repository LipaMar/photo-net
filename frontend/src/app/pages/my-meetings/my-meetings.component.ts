import {Component, OnDestroy, OnInit} from '@angular/core';
import {ScheduleService} from "../../services/schedule.service";
import {LoginService} from "../../login/login.service";
import {SubscriptionContainer} from "../../core/utils/subscription-container";
import {MeetingDisplay, MeetingDto, MeetingStatus, ScheduleDto} from "../../core/models/profile.models";
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
  meetingsToDisplay: MeetingDisplay[] = [];
  displayColumns = [{display: "Fotograf", val: 'owner'},
    {display: "Data", val: 'date'},
    {display: "Cena", val: 'price'},
    {display: "Godzina", val: 'timeStart'},
    {display: "Status", val: 'statusDisplay'}];
  columns = ['owner', 'date', 'timeStart', 'price', 'statusDisplay'];
  expandedElement: MeetingDto | null;

  constructor(private scheduleService: ScheduleService,
              private loginService: LoginService) {
  }

  ngOnInit(): void {
    this.subscriptions.add = this.loginService.doGetIsLogged().subscribe(info => {
      this.loggedUser = info.userName;
      this.updateMeetings();
    });
  }

  private updateMeetings() {
    const schedule = this.scheduleService.getSchedule(this.loggedUser);
    const myMeetings = this.scheduleService.getMyMeetings();
    this.subscriptions.add = forkJoin([schedule, myMeetings]).subscribe(([schedule, myMeetings]: [ScheduleDto, MeetingDto[]]) => {
      this.schedule = schedule;
      this.myMeetings = myMeetings;
      const meetings = schedule.meetings ? schedule.meetings : [];
      this.allMeetings = meetings.concat(myMeetings).filter(meeting => meeting.status !== FREE);
      this.meetingsToDisplay = this.mapMeetingDtoListToDisplayable(this.allMeetings);
    });
  }

  ngOnDestroy(): void {
    this.subscriptions.dispose();
  }

  rejectMeeting(meetingId: number) {
    this.subscriptions.add = this.scheduleService.updateMeetingStatus(meetingId, CANCELED).subscribe(() => this.updateMeetings());
  }

  acceptMeeting(meetingId: number) {
    this.subscriptions.add = this.scheduleService.updateMeetingStatus(meetingId, ACCEPTED).subscribe(() => this.updateMeetings());
  }

  cancelMeeting(meetingId: number) {
    this.subscriptions.add = this.scheduleService.updateMeetingStatus(meetingId, CANCELED).subscribe(() => this.updateMeetings());
  }

  onFilteredList(event: MeetingDto[]) {
    this.meetingsToDisplay = this.mapMeetingDtoListToDisplayable(event);
  }

  mapMeetingDtoListToDisplayable(meetings: MeetingDto[]) {
    const result: MeetingDisplay[] = [];
    meetings.filter(meeting=>meeting.status!=MeetingStatus.DELETED).forEach(dto => {
      const meetingDisplay = new MeetingDisplay(dto, this.translateStatus(dto.status));
      result.push(meetingDisplay);
    });
    result.sort((a, b) => new Date(a.date) > new Date(b.date) ? 1 : -1);
    result.map(value => {
      value.statusDisplay = this.translateStatus(value.status);
      value.timeStart = value.timeStart.slice(0, -3);
      value.price = value.price ? value.price + " zł" : "";
    });
    return result;
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

  rowColor(element: MeetingDisplay, base: string = "") {
    let result = base;
    result += element.owner === this.loggedUser ? " bg-brown " : "";
    if (element.status === MeetingStatus.ARCHIVAL ||
      element.status === MeetingStatus.CANCELED) {
      result += " bg-darken";
    } else if (this.calculateDiff(element.date) <= 1) {
      result += " bg-danger";
    }
    return result;
  }

  calculateDiff(dateSent: any) {
    let currentDate = new Date();
    dateSent = new Date(dateSent);
    return Math.floor((Date.UTC(dateSent.getFullYear(), dateSent.getMonth(), dateSent.getDate()) - Date.UTC(currentDate.getFullYear(), currentDate.getMonth(), currentDate.getDate())) / (1000 * 60 * 60 * 24));
  }

  isExpendable(element: MeetingDisplay, expandedElement: any) {
    return element == expandedElement &&
    element.status !== CANCELED &&
    element.status !== ARCHIVAL ? 'expanded' : 'collapsed';
  }
}
