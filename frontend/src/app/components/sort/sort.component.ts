import {AfterViewInit, Component, EventEmitter, Input, OnInit, Output, ViewChild} from '@angular/core';
import {SortOption} from "../../core/models/discover.models";
import {MatSelect} from "@angular/material/select";
import {SortParams} from "../../core/models/basic.models";

@Component({
  selector: 'app-sort',
  templateUrl: './sort.component.html',
  styleUrls: ['./sort.component.scss']
})
export class SortComponent implements OnInit, AfterViewInit {

  @Input() fields: SortOption[];
  @Input() order: SortOption[] = [
    {value: "asc", display: "rosnąca"}, {value: "desc", display: "malejąca"}
  ];
  @Output() sortParams = new EventEmitter<SortParams>();
  @ViewChild('fieldMatSelect') fieldMatSelect: MatSelect;
  @ViewChild('orderMatSelect') orderMatSelect: MatSelect;
  sortData: SortParams = {field: "", order: ""};

  constructor() {
  }

  ngOnInit(): void {
  }

  sort() {
    this.sortParams.emit(this.sortData)
  }

  ngAfterViewInit(): void {
    this.fieldMatSelect.valueChange.subscribe(change =>
      this.sortData.field = change)
    this.orderMatSelect.valueChange.subscribe(change =>
      this.sortData.order = change)
  }

}
