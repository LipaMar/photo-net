import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Credentials, LoggedDto} from "../core/models/login.models";
import {Observable, Subject} from "rxjs";
import {endpoints} from "../core/const/consts";

@Injectable({
  providedIn: 'root'
})
export class LoginService {

  onLogin$ = new Subject<any>();

  constructor(private http: HttpClient) {
  }

  login(credentials: Credentials): Observable<any> {
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        'Authorization': "Basic " + this.getToken(credentials)
      })
    };
    return this.http.get<any>(endpoints.login, httpOptions)
  };

  getToken(credentials: Credentials): string {
    return btoa(credentials.username + ':' + credentials.password);
  }

  doGetIsLogged(): Observable<LoggedDto> {
    return this.http.get<LoggedDto>(endpoints.login);
  }

  logout() {
    localStorage.removeItem("token");
  }
}
