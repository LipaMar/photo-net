import {Component, EventEmitter, Input, OnInit, Output, ViewChild} from '@angular/core';
import {DiscoverFilters} from "../../core/models/discover.models";
import {FormControl, FormGroup} from "@angular/forms";
import {ChipsSelectComponent} from "../chips-select/chips-select.component";

@Component({
  selector: 'filter',
  templateUrl: './filter.component.html',
  styleUrls: ['./filter.component.scss']
})
export class FilterComponent implements OnInit {

  @Input() categories: string[];
  @Output() filters = new EventEmitter<DiscoverFilters>();
  @Input() ratingFilter: boolean = false;
  @ViewChild("category") categoryFilter: ChipsSelectComponent;

  filtersParam = new DiscoverFilters();

  filterForm = new FormGroup({
    name: new FormControl(),
    rate: new FormControl(),
    category: new FormControl()
  });

  constructor() {
  }

  ngOnInit(): void {
  }

  filter() {
    const name = this.filterForm.get("name")?.value;
    this.filtersParam.userName = name ? name : "";
    this.filters.emit(this.filtersParam);
  }

  resetFilters() {
    this.filtersParam.categories = this.categoryFilter.resetTo([]);
    this.filtersParam.ratingMoreThan = -1;
    this.filterForm.reset();
  }
}
