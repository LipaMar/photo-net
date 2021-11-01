import {Component, Input, OnInit} from '@angular/core';
import {InputTypes} from "../form.models";

@Component({
  selector: 'app-input',
  templateUrl: './input.component.html',
  styleUrls: ['./input.component.scss']
})
export class InputComponent implements OnInit {
@Input() label = "";
@Input() type = InputTypes.TEXT;

  constructor() { }

  ngOnInit(): void {
  }

}
