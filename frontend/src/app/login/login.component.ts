import {Component, Injectable, OnInit} from '@angular/core';
import {LoginService} from "./login.service";
import {Credentials} from "../core/models/login.models";
import {FormBuilder, Validators} from "@angular/forms";
import {ToastrService} from "ngx-toastr";
import {ModalConfig} from "../components/modal/modal.config";
import {TranslateService} from "@ngx-translate/core";
import {AppToastrService} from "../core/toastr.service";
import {Router} from "@angular/router";
import {routes} from "../core/const/consts";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
@Injectable({  //TODO:tak sie chyba nie robi :(
  providedIn: 'root'
})
export class LoginComponent implements OnInit, ModalConfig {

  registerRef = routes.register;
  homeRef = routes.home;

  modalTitle: string = 'page.login.header';
  logged: Promise<boolean>;
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
    const credentials = new Credentials(this.form.controls.login.value,this.form.controls.password.value);
    this.service.login(credentials)
      .subscribe(data => {
        if(data && !data.active){
          this.showBannedAccountMess();
        }
        else if (data) {
            this.showSuccessMess();
            localStorage.setItem("token", this.service.getToken(credentials));
            localStorage.setItem("isLogged", 'true');
            this.service.onLogin$.next(true);
            this.service.onLogin$.complete();
            this.logged = Promise.resolve(true);
            this.router.navigateByUrl(this.homeRef);
          } else {
            this.showLoginFailureMess();
          }
        },
        (error => {
          if (error.status == 401){
            this.showLoginFailureMess();
            localStorage.removeItem("token");
            localStorage.setItem("isLogged", 'false');
          }
        })
      );
  }

  showSuccessMess() {
    this.toastr.success('message.login.success');
  }

  showLoginFailureMess() {
    this.toastr.error("message.login.failure");
  }

  showBannedAccountMess() {
    this.toastr.error("message.login.bannedAccount");
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
