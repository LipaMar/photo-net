import {Component, EventEmitter, Input, OnChanges, OnInit, Output, SimpleChanges, ViewChild} from '@angular/core';
import {MatChip} from "@angular/material/chips";
import {MatChipList} from "@angular/material/chips/chip-list";

@Component({
  selector: 'chips-select',
  templateUrl: './chips-select.component.html',
  styleUrls: ['./chips-select.component.scss']
})
export class ChipsSelectComponent implements OnInit, OnChanges {

  @Output() selected = new EventEmitter<string[]>();
  @Input() labels: any[] = [];
  @Input() checked: string[] = [];
  @Input() disabled: boolean = false;
  @ViewChild("chipList") chipList: MatChipList;

  values: string[] = [];

  chips = new Map();

  constructor() {
  }

  ngOnInit(): void {
    this.populateChips();
  }

  private populateChips() {
    this.values = [];
    this.labels.forEach(label => {
      this.chips.set(label, this.checked.indexOf(label) > -1);
    })
    this.chips.forEach((value, key) => {
      const chip = this.chipList.chips.find(item => item.value == key);
      if (value) {
        this.values.push(key);
        chip?.select();
      } else {
        chip?.deselect();
      }
    });
  }

  resetTo(selected: string[]) {
    this.checked = [];
    selected.forEach(value => this.checked.push(value))
    this.populateChips();
    return this.values;
  }

  refresh() {
    this.chips = new Map();
    this.populateChips();
  }

  toggleSelection(chip: MatChip) {
    if (this.disabled) {
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
