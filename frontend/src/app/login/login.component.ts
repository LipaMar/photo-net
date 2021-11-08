import {Component, OnInit} from '@angular/core';
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

  model = new Credentials('', '');
  form = this.fb.group({
    login: ["", [Validators.required]],
    password: ["", [Validators.required]]
  });

  constructor(private service: LoginService,
              private fb: FormBuilder,
              private toastr: ToastrService,
              private router: Router) {
  }

  ngOnInit(): void {
  }

  submitLogin() {
    this.service.login(this.model)
      .subscribe(data => {
          console.log(data);
          if (data) {
            this.showSuccessMess();
            this.router.navigateByUrl("/home");
          }
        },
        (error => {
          if (error.status == 401)
            this.showLoginFailureMess();
        })
      );
  }

  showSuccessMess() {
    this.toastr.success("Pomyślnie zalogowano");
  }

  showLoginFailureMess() {
    this.toastr.error("Błędny login lub hasło");
  }

}
