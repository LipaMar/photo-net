import {Component, OnInit} from '@angular/core';
import {RegisterService} from "./register.service";
import {RegisterDto} from "./register.models";
import {ToastrService} from "ngx-toastr";
import {Router} from "@angular/router";
import * as bcrypt from "bcryptjs";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent implements OnInit {

  registerDto = new RegisterDto('','','');
  pass = '';
  repeatPass = '';

  constructor(private service: RegisterService,
              private toastr: ToastrService,
              private router: Router) {
  }

  ngOnInit(): void {
  }

  register() {
    const salt = bcrypt.genSaltSync(10);
    this.registerDto.password = bcrypt.hashSync(this.pass, salt);
    this.service.register(this.registerDto).subscribe(response=>{
        this.showSuccessMess();
        this.router.navigateByUrl("/login");
    },
      (error => {
        if (error.status == 401)
          this.toastr.error("message.register.failure");
        if (error.status == 409)
          this.toastr.error("message.register.alreadyExist");
      })
    );
  }

  showSuccessMess() {
    this.toastr.success('message.register.success');
  }

}
