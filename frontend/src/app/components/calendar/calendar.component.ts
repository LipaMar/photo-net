import {Component, Input, OnInit, ViewChild, ViewEncapsulation} from '@angular/core';
import {MeetingStatus, ScheduleDto} from "../../core/models/profile.models";
import {MatCalendarCellClassFunction} from "@angular/material/datepicker/calendar-body";
import {ModalComponent} from "../modal/modal.component";
import {ModalConfig} from "../modal/modal.config";
import {CalendarModalConfig} from "./CalendarModalConfig";
import {DatePipe} from "@angular/common";

@Component({
  selector: 'calendar',
  templateUrl: './calendar.component.html',
  styleUrls: ['./calendar.component.scss'],
  encapsulation: ViewEncapsulation.None,
})
export class CalendarComponent implements OnInit {

  @Input() schedule: ScheduleDto;
  @Input() isMyProfile: boolean;
  @ViewChild('modal') private modalComponent: ModalComponent;

  dateFilter = (date: Date) => true;
  minDate = new Date();
  dateClass: MatCalendarCellClassFunction<Date>;
  calendarEditMode: boolean = false;
  modalConfig: ModalConfig = new CalendarModalConfig();

  hoursSelected: string[] = [];

  constructor(private datePipe: DatePipe) {
    this.modalConfig.onClose = () => this.onScheduleHourSave();
    this.modalConfig.onDismiss = () => this.onScheduleHourCancel();
  }

  ngOnInit(): void {
    this.dateClass = (cellDate, view) => this.computeClass(cellDate, view);
    if (!this.isMyProfile) {
      this.dateFilter = (date: Date) => this.filterDate(date);
    }
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

  switchCalendarEditMode() {
    this.calendarEditMode = !this.calendarEditMode;
  }

  async openModal() {
    return await this.modalComponent.open()
  }

  async addMeeting(pickedDate: Date | null) {
    const transform = this.datePipe.transform(pickedDate, 'EEEE, d MMMM y', undefined, 'pl-PL');
    if (transform) {
      this.modalConfig.modalTitle = transform;
      await this.openModal();
    }
  }

  onScheduleHourSave(): boolean {
    return true;
  }

  onScheduleHourCancel(): boolean {
    return true;
  }
}
