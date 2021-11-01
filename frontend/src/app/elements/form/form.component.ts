import {Component, Input, OnInit} from '@angular/core';
import {FormField, MethodType} from "./form.models";

@Component({
  selector: 'app-form',
  templateUrl: './form.component.html',
  styleUrls: ['./form.component.scss']
})
export class FormComponent implements OnInit {

  @Input() formFields: FormField[] = [];
  @Input() method: MethodType = MethodType.GET;

  constructor() {
  }

  ngOnInit(): void {
  }

}
