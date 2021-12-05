import { Injectable } from '@angular/core';
import {TranslateService} from "@ngx-translate/core";
import {ToastrService} from "ngx-toastr";

@Injectable({
  providedIn: 'root'
})
export class AppToastrService {

  constructor(private toastr: ToastrService,
              private translate: TranslateService) { }

  error(message: string){
    this.toastr.error(this.translate.instant(message), undefined, {positionClass: "toastr-position"} );
  }
  success(message: string){
    this.toastr.success(this.translate.instant(message), undefined, {positionClass: "toastr-position"} );
  }
}
