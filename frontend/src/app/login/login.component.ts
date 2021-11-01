import {Component, OnInit} from '@angular/core';
import {FormField, InputTypes, MethodType} from "../elements/form/form.models";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  constructor() {
  }

  method = MethodType.POST;
  formFields: FormField[] = [
    {label: "Login", type: InputTypes.TEXT},
    {label: "Hasło", type: InputTypes.PASSWORD},
  ];

  ngOnInit(): void {
  }

}
