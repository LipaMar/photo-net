import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {DiscoverFilters} from "../../core/models/discover.models";
import {MatChip} from "@angular/material/chips";

@Component({
  selector: 'filter',
  templateUrl: './filter.component.html',
  styleUrls: ['./filter.component.scss']
})
export class FilterComponent implements OnInit {

  @Input() categories: string[];
  @Output() filters = new EventEmitter<DiscoverFilters>();
  @Input() ratingFilter: boolean = false;
  filtersParam = new DiscoverFilters();

  constructor() {
  }

  ngOnInit(): void {
  }

  filter() {
    this.filters.emit(this.filtersParam);
  }
}
