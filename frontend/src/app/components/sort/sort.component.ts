import {AfterViewInit, Component, EventEmitter, Input, OnInit, Output, ViewChild} from '@angular/core';
import {SortOption} from "../../core/models/discover.models";
import {MatSelect} from "@angular/material/select";
import {SortParams} from "../../core/models/basic.models";
import {FormControl, FormGroup} from "@angular/forms";

@Component({
  selector: 'app-sort',
  templateUrl: './sort.component.html',
  styleUrls: ['./sort.component.scss']
})
export class SortComponent implements OnInit {

  @Input() fields: SortOption[];
  @Input() listToSort: any[];
  @Input() order: SortOption[] = [
    {value: "asc", display: "rosnąca"}, {value: "desc", display: "malejąca"}
  ];
  @Output() sortParams = new EventEmitter<SortParams>();
  @Output() sortedList = new EventEmitter<any[]>();

  form = new FormGroup({
    field:new FormControl(),
    order:new FormControl()
  });

  constructor() {
  }

  ngOnInit(): void {
    this.form.controls.order.setValue(this.order[0].value)
  }

  sort() {
    const sortData: SortParams = {field: this.form.controls.field.value, order: this.form.controls.order.value};
    this.sortParams.emit(sortData);
    if (this.listToSort) {
      const field = this.fields.filter(value => value.value === sortData.field).shift();
      const order = sortData.order === "asc" ? 1 : -1;
      this.listToSort.sort((a, b) => {
        if (field && field.comparator && field.extractor) {
          return order * field.comparator(field.extractor(a), field.extractor(b));
        }
        return 1;
      });
    }
  }
}
