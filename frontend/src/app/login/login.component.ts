import {Component, Injectable, OnInit} from '@angular/core';
import {LoginService} from "./login.service";
import {Credentials} from "./login.models";
import {FormBuilder, Validators} from "@angular/forms";
import {ToastrService} from "ngx-toastr";
import {ModalConfig} from "../components/modal/modal.config";
import {TranslateService} from "@ngx-translate/core";
import {AppToastrService} from "../core/toastr.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
@Injectable({  //TODO:tak sie chyba nie robi :(
  providedIn: 'root'
})
export class LoginComponent implements OnInit, ModalConfig {
  modalTitle: string = 'page.login.header';
  logged: Promise<boolean>;
  credentials = new Credentials('', '');
  form = this.fb.group({
    login: ["", [Validators.required]],
    password: ["", [Validators.required]]
  });

  constructor(private service: LoginService,
              private fb: FormBuilder,
              private toastr: AppToastrService,
              private router: Router) {
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
            this.service.onLogin$.next(true);
            this.service.onLogin$.complete();
            this.logged = Promise.resolve(true);
            this.router.navigateByUrl("/home");
          } else {
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

  async shouldClose() {
    return this.service.onLogin$.toPromise().then(() => true);
  }

  hideCloseButton(): boolean {
    return true;
  }

  hideDismissButton(): boolean {
    return true;
  }



}
