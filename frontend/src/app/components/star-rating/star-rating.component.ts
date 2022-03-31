import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';

@Component({
  selector: 'star-rating',
  templateUrl: './star-rating.component.html',
  styleUrls: ['./star-rating.component.scss']
})
export class StarRatingComponent implements OnInit {

  @Input() rate: number;
  @Output() rateChange = new EventEmitter<number>();
  @Input() readonly: boolean = true;
  rateVal: number | null = null;

  constructor() {
  }

  ngOnInit(): void {
  }

  onChange(ratePicked: number) {
    this.rateChange.emit(ratePicked);
    this.rateVal = ratePicked;
  }
}
