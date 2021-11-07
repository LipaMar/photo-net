import {Component, OnInit} from '@angular/core';
import {FormField, InputTypes} from "../elements/form/form.models";
import {LoginService} from "./login.service";
import {Credentials} from "./login.models";
import {FormBuilder, Validators} from "@angular/forms";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  model = new Credentials('', '');
  form = this.fb.group({
    login: ["", [Validators.required]],
    password: ["", [Validators.required]]
  });

  formFields: FormField[] = [
    {label: "Login", type: InputTypes.TEXT, field: 'login'},
    {label: "Hasło", type: InputTypes.PASSWORD, field: 'password'},
  ];

  constructor(private service: LoginService, private fb: FormBuilder) {
  }

  ngOnInit(): void {
  }

  newCredentials() {
    this.model = new Credentials('', '');
  }

  submitLogin() {
    console.log(this.model)
    this.service.login(this.model).subscribe(data => {
        console.log(data);
      }
    );
  }

}
