import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {RegisterDto} from "./register.models";
import {Observable} from "rxjs";
import {endpoints} from "../../core/const/consts";

@Injectable({
  providedIn: 'root'
})
export class RegisterService {

  constructor(private http: HttpClient) {
  }

  register(dto: RegisterDto): Observable<any> {
    return this.http.post(endpoints.register, dto);
  }
}
