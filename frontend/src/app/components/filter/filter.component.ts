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
  filtersParam = new DiscoverFilters();

  constructor() {
  }

  ngOnInit(): void {
  }

  toggleSelection(chip: MatChip) {
    chip.toggleSelected();
    let chipVal = chip.value;
    if (!this.filtersParam.categories)
      return;
    if (chip.selected) {
      this.filtersParam.categories.push(chipVal);
    } else {
      this.removeFirst(this.filtersParam.categories, chipVal)
    }
  }

  private removeFirst<T>(array: T[], toRemove: T): void {
    const index = array.indexOf(toRemove);
    if (index !== -1) {
      array.splice(index, 1);
    }
  }

  filter() {
    this.filters.emit(this.filtersParam);
  }
}
