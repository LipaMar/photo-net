import {Component, EventEmitter, OnInit, Output} from '@angular/core';
import {LoginService} from "./login.service";
import {Credentials} from "./login.models";
import {FormBuilder, Validators} from "@angular/forms";
import {ToastrService} from "ngx-toastr";
import {Router} from "@angular/router";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {
  credentials = new Credentials('', '');
  form = this.fb.group({
    login: ["", [Validators.required]],
    password: ["", [Validators.required]]
  });

  constructor(private service: LoginService,
              private fb: FormBuilder,
              private toastr: ToastrService,
              private router: Router,
              private loginService: LoginService) {
  }

  ngOnInit(): void {
  }

  submitLogin() {
    this.service.login(this.credentials)
      .subscribe(data => {
          if (data) {
            this.showSuccessMess();
            localStorage.setItem("token", this.service.getToken(this.credentials));
            localStorage.setItem("isLogged", 'true');
            this.loginService.onLogin$.next(true);
            this.router.navigateByUrl("/home");
          }
          else {
            this.showLoginFailureMess();
          }
        },
        (error => {
          if (error.status == 401)
            this.showLoginFailureMess();
        })
      );
  }

  showSuccessMess() {
    this.toastr.success('message.login.success');
  }

  showLoginFailureMess() {
    this.toastr.error("message.login.failure");
  }

}
