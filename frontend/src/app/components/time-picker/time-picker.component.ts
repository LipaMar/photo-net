import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';

@Component({
  selector: 'time-picker',
  templateUrl: './time-picker.component.html',
  styleUrls: ['./time-picker.component.scss']
})
export class TimePickerComponent implements OnInit {

  @Input() hours: string[] = [];
  @Output() selectedChange = new EventEmitter<string[]>();
  @Input() selected: string[] = [];

  constructor() {
  }

  ngOnInit(): void {
    if(this.hours.length === 0 ){
      for(let i=0; i<24; i++){
        i<10 ? this.hours.push('0'+i+':00') : this.hours.push(i+':00');
      }
    }
  }
}
