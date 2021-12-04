import {Component, Injectable, OnInit} from '@angular/core';
import {LoginService} from "./login.service";
import {Credentials} from "./login.models";
import {FormBuilder, Validators} from "@angular/forms";
import {ToastrService} from "ngx-toastr";
import {ModalConfig} from "../components/modal/modal.config";
import {TranslateService} from "@ngx-translate/core";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
@Injectable({ 
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
              private toastr: ToastrService,
              private translate: TranslateService) {
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
    this.toastr.success(this.translate.instant('message.login.success'));
  }

  showLoginFailureMess() {
    this.toastr.error(this.translate.instant("message.login.failure"));
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
