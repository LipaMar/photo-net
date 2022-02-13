import {AfterViewInit, Component, EventEmitter, Input, OnInit, Output, ViewChild} from '@angular/core';
import {SortOption} from "../../core/models/discover.models";
import {MatSelect} from "@angular/material/select";
import {SortParams} from "../../core/models/basic.models";
import {MeetingDto} from "../../core/models/profile.models";

@Component({
  selector: 'app-sort',
  templateUrl: './sort.component.html',
  styleUrls: ['./sort.component.scss']
})
export class SortComponent implements OnInit, AfterViewInit {

  @Input() fields: SortOption[];
  @Input() listToSort: MeetingDto[];
  @Input() order: SortOption[] = [
    {value: "asc", display: "rosnąca"}, {value: "desc", display: "malejąca"}
  ];
  @Output() sortParams = new EventEmitter<SortParams>();
  @Output() sortedList = new EventEmitter<MeetingDto[]>();
  @ViewChild('fieldMatSelect') fieldMatSelect: MatSelect;
  @ViewChild('orderMatSelect') orderMatSelect: MatSelect;
  sortData: SortParams = {field: "", order: ""};

  constructor() {
  }

  ngOnInit(): void {
  }

  sort() {
    this.sortParams.emit(this.sortData);
    if (this.listToSort) {
      const field = this.fields.filter(value => value.value === this.sortData.field).shift();
      const order = this.sortData.order === "asc" ? 1 : -1;
      this.listToSort.sort((a, b) => {
        if(field && field.comparator){
          return order * field.comparator(a,b);
        }
        return 1;
      });
    }
  }


  ngAfterViewInit(): void {
    this.fieldMatSelect.valueChange.subscribe(change =>
      this.sortData.field = change);
    this.orderMatSelect.valueChange.subscribe(change =>
      this.sortData.order = change);
  }

}
