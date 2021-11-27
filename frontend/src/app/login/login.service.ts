import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {environment} from "../../environments/environment";
import {Credentials, LoggedDto} from "./login.models";
import {Observable, Subject} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class LoginService {

  onLogin$ = new Subject<any>();

  constructor(private http: HttpClient) {
  }

  login(credentials: Credentials): Observable<Boolean> {
    let token = this.getToken(credentials);
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        'Authorization': "Basic " + token
      })
    };
    return this.http.get<any>(`${environment.apiUrl}/login`, httpOptions)
  };

  getToken(credentials: Credentials):string{
    return btoa(credentials.username + ':' + credentials.password);
  }

  doGetIsLogged(): Observable<LoggedDto> {
    return this.http.get<LoggedDto>(environment.apiUrl + '/login');
  }

  logout() {
    localStorage.removeItem("token");
  }
}
