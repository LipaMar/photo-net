import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {UserInfoDto} from "../../core/models/user-info.models";
import {endpoints} from "../../core/const/consts";

@Injectable({
  providedIn: 'root'
})
export class AdminPanelService {

  constructor(private http:HttpClient) { }

  getUsersList(){
    return this.http.get<UserInfoDto[]>(endpoints.users_info)
  }

  ban(element: UserInfoDto) {
    return this.http.post(endpoints.users_info, element.userName);
  }

  unban(element: UserInfoDto) {
    return this.http.put(endpoints.users_info, element.userName);
  }
}
