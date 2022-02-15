import {
  Component,
  EventEmitter,
  Input,
  OnChanges,
  OnInit,
  Output,
  SimpleChanges,
  ViewChild,
  ViewEncapsulation
} from '@angular/core';
import {MeetingDto, MeetingStatus, ScheduleDto} from "../../core/models/profile.models";
import {MatCalendarCellClassFunction} from "@angular/material/datepicker/calendar-body";
import {ModalComponent} from "../modal/modal.component";
import {CleanModalConfig, ModalConfig} from "../modal/modal.config";
import {CalendarModalConfig} from "./CalendarModalConfig";
import {DatePipe} from "@angular/common";
import {MatCalendar} from "@angular/material/datepicker";
import {TimePickerComponent} from "../time-picker/time-picker.component";

@Component({
  selector: 'calendar',
  templateUrl: './calendar.component.html',
  styleUrls: ['./calendar.component.scss'],
  encapsulation: ViewEncapsulation.None,
})
export class CalendarComponent implements OnInit, OnChanges {

  @Input() schedule: ScheduleDto;
  @Input() isMyProfile: boolean;
  @Output() savedSchedule = new EventEmitter<{ meetings: MeetingDto[], saveDate: any }>();
  @Output() clientBooking = new EventEmitter<{ date: string, hour: string }>();
  @ViewChild('modal') private modalComponent: ModalComponent;
  @ViewChild('otherProfileModal') private otherProfileModalComponent: ModalComponent;
  @ViewChild(MatCalendar) calendar: MatCalendar<Date>;
  @ViewChild(TimePickerComponent) timePicker: TimePickerComponent;

  dateFilter = (date: Date) => true;
  minDate = new Date();
  dateClass: MatCalendarCellClassFunction<Date>;
  modalConfig: ModalConfig = new CalendarModalConfig();

  hoursSelected: string[] = [];
  dateSelected: Date;
  cleanConfig = new CleanModalConfig();
  otherProfileMeetingHours: string[] = [];

  constructor(private datePipe: DatePipe) {
    this.modalConfig.onClose = () => this.onScheduleHourSave();
  }

  ngOnInit(): void {
    this.dateClass = (cellDate, view) => this.computeClass(cellDate, view);
    if (!this.isMyProfile) {
      this.dateFilter = (date: Date) => this.filterFreeDates(date);
    }
  }

  isDateEqual(date1: Date, date2: Date): boolean {
    return date1.getFullYear() == date2.getFullYear() &&
      date1.getDate() == date2.getDate() &&
      date1.getMonth() == date2.getMonth();
  }

  computeClass(cellDate: Date, view: "month" | "year" | "multi-year") {
    let dateClass = '';
    if (view === 'month' && this.schedule.meetings) {
      this.schedule.meetings.forEach(meeting => {
        if (this.isDateEqual(new Date(meeting.date), cellDate)) {
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

  private filterFreeDates(date: Date) {
    const {meetings} = this.schedule;
    if (this.schedule && meetings) {
      return meetings.some(meeting => this.hasMeetingStatus(meeting, date, [MeetingStatus.FREE]));
    }
    return false;
  }

  private hasMeetingStatus(meeting: MeetingDto, date: Date, statuses: MeetingStatus[]) {
    return this.isDateEqual(new Date(meeting.date), date) && statuses.some(status => status === meeting.status);
  }

  async selectDateOnCalendar(pickedDate: Date | null) {
    if (this.isMyProfile) {
      await this.editTimeSchedule(pickedDate);
    } else {
      await this.selectAvailableHour(pickedDate);
    }
  }

  async editTimeSchedule(pickedDate: Date | null) {
    const title = this.getTitle(pickedDate);
    const {ACCEPTED, NEW, ARCHIVAL, CANCELED, FREE} = MeetingStatus;
    if (title && pickedDate) {
      this.modalConfig.modalTitle = title;
      this.timePicker.reset();
      this.hoursSelected = this.getDtoHoursForDate(pickedDate, [FREE]);
      this.timePicker.removeValues(this.getDtoHoursForDate(pickedDate, [ACCEPTED, NEW, ARCHIVAL, CANCELED]));
      this.dateSelected = pickedDate;
      await this.modalComponent.open();

    }
  }

  private getTitle(pickedDate: Date | null) {
    return this.datePipe.transform(pickedDate, 'EEEE, d MMMM y', undefined, 'pl-PL');
  }

  onScheduleHourSave(): boolean {
    let meetings: MeetingDto[] = this.createMeetings();
    this.savedSchedule.emit({meetings: meetings, saveDate: this.datePipe.transform(this.dateSelected, 'yyyy-MM-dd')});
    return true;
  }

  OnHourSelected(hours: any) {
    this.hoursSelected = hours;
  }

  private getDtoHoursForDate(pickedDate: Date, statuses: MeetingStatus[]): string[] {
    const result: string[] = [];
    this.schedule.meetings?.forEach(meeting => {
      if (this.hasMeetingStatus(meeting, pickedDate, statuses)) {
        result.push(meeting.timeStart.slice(0, -3));
      }
    });
    return result;
  }

  private createMeetings() {
    const result: MeetingDto[] = [];
    const date = this.datePipe.transform(this.dateSelected, 'yyyy-MM-dd');
    if (date) {
      this.hoursSelected.forEach(hour => {
        result.push({
          date: date,
          status: MeetingStatus.FREE,
          timeStart: hour,
          owner: this.schedule.owner
        })
      })
    }
    return result;
  }

  ngOnChanges(changes: SimpleChanges): void {
    if (this.calendar) {
      this.calendar.updateTodaysDate();
    }
  }

  private async selectAvailableHour(pickedDate: Date | null) {
    const title = this.getTitle(pickedDate);
    if (title && pickedDate) {
      this.cleanConfig.modalTitle = title;
      this.dateSelected = pickedDate;
      this.otherProfileMeetingHours = this.getDtoHoursForDate(pickedDate, [MeetingStatus.FREE]);
      this.otherProfileMeetingHours = this.otherProfileMeetingHours.sort((a, b) => parseInt(a) >= parseInt(b) ? 1 : -1);
      await this.otherProfileModalComponent.open();
    }
  }

  onClientSelectedHour(hour: string) {
    this.otherProfileModalComponent.close();
    const date = this.datePipe.transform(this.dateSelected, 'yyyy-MM-dd');
    if (date) {
      this.clientBooking.emit({date: date, hour: hour});
    }
  }
}
