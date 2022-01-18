import {Component, EventEmitter, Input, OnChanges, OnInit, Output, SimpleChanges} from '@angular/core';
import {MatChip} from "@angular/material/chips";

@Component({
  selector: 'chips-select',
  templateUrl: './chips-select.component.html',
  styleUrls: ['./chips-select.component.scss']
})
export class ChipsSelectComponent implements OnInit, OnChanges {

  @Output() selected = new EventEmitter<string[]>();
  @Input() labels: string[] = [];
  @Input() checked: string[] = [];
  @Input() disabled: boolean = false;

  values: string[] = [];

  chips = new Map();

  constructor() { }

  ngOnInit(): void {
    this.populateChips();
  }

  private populateChips() {
    this.values = [];
    this.labels.forEach(label => {
      this.chips.set(label, this.checked.indexOf(label) > -1);
    })
    this.chips.forEach((value, key) => {
      if (value) {
        this.values.push(key)
      }
    });
  }

  resetTo(selected: string[]){
    this.checked = [];
    selected.forEach(value => this.checked.push(value))
    this.populateChips();
  }

  toggleSelection(chip: MatChip) {
    if(this.disabled){
      return;
    }
    chip.toggleSelected();
    let chipVal = chip.value;
    if (!this.values)
      return;
    if (chip.selected) {
      this.values.push(chipVal);
    } else {
      this.removeFirst(this.values, chipVal)
    }
    this.selected.emit(this.values);
  }

  private removeFirst<T>(array: T[], toRemove: T): void {
    const index = array.indexOf(toRemove);
    if (index !== -1) {
      array.splice(index, 1);
    }
  }

  ngOnChanges(changes: SimpleChanges): void {
    this.populateChips();
  }

}
