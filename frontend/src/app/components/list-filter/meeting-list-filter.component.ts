import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {MeetingDto, MeetingStatus} from "../../core/models/profile.models";
import {MatSelectChange} from "@angular/material/select";

enum MeetingTypes {
  ALL,
  PHOTOGRAPHER,
  CLIENT
}

const {CLIENT, PHOTOGRAPHER, ALL} = MeetingTypes;

@Component({
  selector: 'list-filter',
  templateUrl: './meeting-list-filter.component.html',
  styleUrls: ['./meeting-list-filter.component.scss']
})
export class MeetingListFilterComponent implements OnInit {

  @Input() listToFilter: MeetingDto[] = [];
  @Input() user: string;
  @Output() filteredList = new EventEmitter<MeetingDto[]>();

  meetingTypes = new Map([["-", ALL], ["Fotograf", PHOTOGRAPHER], ["Klient", CLIENT]]);
  statuses = new Map([
    ["Do akceptacji", {type: "Do akceptacji", selected: false}],
    ["Zaakceptowane", {type: "Zaakceptowane", selected: false}],
    ["Odwołane", {type: "Odwołane", selected: false}],
    ["Archiwalne", {type: "Archiwalne", selected: false}]
  ]);

  selectedType: MeetingTypes = ALL;

  constructor() {
  }

  ngOnInit(): void {
  }


  filter() {

    this.filteredList.emit(this.listToFilter.filter(meeting => {
      const selectedStatuses = this.getSelectedStatuses();
      return (selectedStatuses.length == 0 || selectedStatuses.some(value => value === meeting.status)) &&
        this.meetingTypeMatching(meeting.userBooked);
    }));

  }

  private getSelectedStatuses(): MeetingStatus[] {
    const result: any[] = [];
    this.statuses.forEach(value => {
      if (value.selected) {
        result.push(value.type);
      }
    });
    return result;
  }

  private meetingTypeMatching(userBooked: any): boolean {
    const isClient = userBooked === this.user;
    switch (this.selectedType) {
      case CLIENT:
        return isClient;
      case PHOTOGRAPHER:
        return !isClient;
      default:
        return true;
    }
  }

  onTypeSelected(event: MatSelectChange) {
    this.selectedType = event.value;
  }
}
