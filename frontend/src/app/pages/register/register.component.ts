import {Component, OnInit} from '@angular/core';
import {RegisterService} from "./register.service";
import {RegisterDto} from "../../core/models/register.models";
import {Router} from "@angular/router";
import * as bcrypt from "bcryptjs";
import {AppToastrService} from "../../core/toastr.service";
import {AbstractControl, FormControl, FormGroup, ValidationErrors, ValidatorFn, Validators} from "@angular/forms";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent implements OnInit {

  emailPattern = '^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$';

  checkPasswords: ValidatorFn = (group: AbstractControl): ValidationErrors | null => {
    let pass = group.get("password")?.value;
    let confirmPass = group.get("passwordConfirm")?.value;
    return pass === confirmPass ? null : {notSame: true}
  }

  form = new FormGroup({
    username: new FormControl("", [Validators.required]),
    email: new FormControl("", [Validators.required, Validators.email]),
    password: new FormControl("", [Validators.required, Validators.minLength(6)]),
    passwordConfirm: new FormControl("", [Validators.required, Validators.minLength(6)]),
  }, {validators: this.checkPasswords});

  constructor(private service: RegisterService,
              private toastr: AppToastrService,
              private router: Router) {
  }

  ngOnInit(): void {
  }

  register() {
    const errors = this.form.errors;
    if (errors && errors["notSame"]) {
      this.toastr.error("Podane hasła nie są identyczne");
      return;
    }
    const {username, email, password} = this.form.controls;
    const salt = bcrypt.genSaltSync(10);
    const pass = bcrypt.hashSync(password.value, salt);
    const registerDto = new RegisterDto(username.value, email.value, true, pass);
    this.service.register(registerDto).subscribe(response => {
        this.toastr.success('message.register.success');
        this.router.navigateByUrl("/home");
      },
      (error => {
        if (error.status == 401)
          this.toastr.error("message.register.failure");
        if (error.status == 409)
          this.toastr.error("message.register.alreadyExist");
      })
    );
  }

}
