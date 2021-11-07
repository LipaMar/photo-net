import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {map} from 'rxjs/operators';
import {environment} from "../../environments/environment";
import {Credentials} from "./login.models";

@Injectable({
  providedIn: 'root'
})
export class LoginService {

  constructor(private httpClient: HttpClient) {
  }

  login(credentials: Credentials) {
    let token = btoa(credentials.username + ':' + credentials.password);
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        'Authorization': "Basic " + token
      })
    };

    return this.httpClient.get<any>(`${environment.apiUrl}/login`,  httpOptions)
      .pipe(map(logged => {
        // store user details and basic auth credentials in local storage to keep user logged in between page refreshes
        // user.authdata = window.btoa(credentials.username + ':' + credentials.password);
        // localStorage.setItem('user', JSON.stringify(user));
        // this.userSubject.next(user);
        console.log(logged);
      }));
  }
}
