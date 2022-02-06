import {Component, EventEmitter, Input, OnInit, Output, ViewChild} from '@angular/core';
import {ChipsSelectComponent} from "../chips-select/chips-select.component";

@Component({
  selector: 'time-picker',
  templateUrl: './time-picker.component.html',
  styleUrls: ['./time-picker.component.scss']
})
export class TimePickerComponent implements OnInit {

  @Output() selectedChange = new EventEmitter<string[]>();
  @Input() selected: string[] = [];
  @ViewChild(ChipsSelectComponent) hourSelect: ChipsSelectComponent;

  hours: string[] = [];

  constructor() {
  }

  ngOnInit(): void {
    this.hours = this.get24h();
  }

  private get24h() {
    const result: string[] = [];
    for (let i = 0; i < 24; i++) {
      i < 10 ? result.push('0' + i + ':00') : result.push(i + ':00');
    }
    return result;
  }

  reset() {
    this.hourSelect.labels = this.get24h();
    this.hourSelect.refresh();
  }

  removeValues(values: string[]) {
    values.forEach(value => {
      let index = this.hourSelect.labels.indexOf(value);
      if (index > -1) {
        this.hourSelect.labels.splice(index, 1);
      }
    })
    this.hourSelect.refresh();
  }
}
