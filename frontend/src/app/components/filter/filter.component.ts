import {AfterViewInit, Component, EventEmitter, OnInit, Output} from '@angular/core';
import {DiscoverFilters} from "../../core/models/discover.models";

@Component({
  selector: 'filter',
  templateUrl: './filter.component.html',
  styleUrls: ['./filter.component.scss']
})
export class FilterComponent implements OnInit {

  @Output() filters = new EventEmitter<DiscoverFilters>();
  filtersParam = new DiscoverFilters();
  constructor() {
  }

  ngOnInit(): void {
  }

  setRateCount(event: any) {
    this.filtersParam.rateCountLessThan = event.value;
  }

  filter() {
    this.filters.emit(this.filtersParam);
  }

}
