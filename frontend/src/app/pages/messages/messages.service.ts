import {Injectable} from '@angular/core';
import {HttpClient, HttpParams} from "@angular/common/http";
import {endpoints} from "../../core/const/consts";
import {ChatDto, NewMessageDto} from "../../core/models/meesage.models";

@Injectable({
  providedIn: 'root'
})
export class MessagesService {

  private _defaultUser: string | null;


  get defaultUser(): string | null {
    const result = this._defaultUser;
    this._defaultUser = null;
    return result;
  }

  set defaultUser(value: string | null) {
    this._defaultUser = value;
  }

  constructor(private http: HttpClient) {
  }

  startNewChat(userName: string) {
    this.defaultUser = userName;
    return this.http.post(endpoints.chat, userName);
  }

  sendMessage(message: NewMessageDto) {
    return this.http.post<NewMessageDto>(endpoints.message, message);
  }

  getAllChats(userName: string) {
    return this.http.get<ChatDto[]>(endpoints.chat, {params: new HttpParams().append("userName", userName)});
  }

}
