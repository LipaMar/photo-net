import {Component, Input, OnInit, ViewEncapsulation} from '@angular/core';
import {MeetingStatus, ScheduleDto} from "../../core/models/profile.models";
import {MatCalendarCellClassFunction} from "@angular/material/datepicker/calendar-body";

@Component({
  selector: 'calendar',
  templateUrl: './calendar.component.html',
  styleUrls: ['./calendar.component.scss'],
  encapsulation: ViewEncapsulation.None,
})
export class CalendarComponent implements OnInit {

  @Input() schedule: ScheduleDto;
  minDate = new Date();
  dateFilter = (date: Date) => {
    return false
  };

  dateClass: MatCalendarCellClassFunction<Date>;

  constructor() {
  }

  ngOnInit(): void {
    this.dateClass = (cellDate, view) => this.computeClass(cellDate, view);
    this.dateFilter = (date: Date) => this.filterDate(date);
  }

  compareDates(date1: Date, date2: Date): boolean {
    return date1.getFullYear() == date2.getFullYear() &&
      date1.getDate() == date2.getDate() &&
      date1.getMonth() == date2.getMonth();

  }

  computeClass(cellDate: Date, view: "month" | "year" | "multi-year") {
    let dateClass = '';
    if (view === 'month') {
      this.schedule.meetings.forEach(meeting => {
        if (this.compareDates(new Date(meeting.date), cellDate)) {
          switch (meeting.status) {
            case MeetingStatus.FREE:
              dateClass = 'free';
              break;
            case MeetingStatus.NEW:
              dateClass = 'new';
              break;
            case MeetingStatus.ARCHIVAL:
              dateClass = 'archival';
              break;
            case MeetingStatus.ACCEPTED:
              dateClass = 'accepted';
              break;
            case MeetingStatus.CANCELED:
              dateClass = 'canceled';
              break;
          }
        }
      })
    }
    return dateClass;
  };

  private filterDate(date: Date) {
    const {meetings} = this.schedule;
    if (this.schedule && meetings) {
      return meetings.some(meeting => this.compareDates(new Date(meeting.date), date));
    }
    return false;
  }
}
