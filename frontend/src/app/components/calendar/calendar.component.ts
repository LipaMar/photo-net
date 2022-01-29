import {Component, Input, OnInit} from '@angular/core';
import {ScheduleDto} from "../../core/models/profile.models";

@Component({
  selector: 'calendar',
  templateUrl: './calendar.component.html',
  styleUrls: ['./calendar.component.scss']
})
export class CalendarComponent implements OnInit {

  @Input() schedule: ScheduleDto;
  minDate = new Date();
  dateFilter = (date: Date) => {
    return false
  };

  constructor() {
  }

  ngOnInit(): void {
    this.dateFilter = (date: Date) => {
      if (this.schedule && this.schedule.meetings) {
        return this.schedule.meetings.some(meeting => this.compareDates(new Date(meeting.date),date));
      }
      return false;
    }
  }

  compareDates(date1: Date, date2: Date): boolean {
    return date1.getFullYear() == date2.getFullYear() &&
      date1.getDate() == date2.getDate() &&
      date1.getMonth() == date2.getMonth();

  }

}
