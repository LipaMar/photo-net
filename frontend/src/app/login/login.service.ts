import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {environment} from "../../environments/environment";
import {Credentials} from "./login.models";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class LoginService {

  constructor(private httpClient: HttpClient) {
  }

  login(credentials: Credentials): Observable<Boolean> {
    let token = btoa(credentials.username + ':' + credentials.password);
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        'Authorization': "Basic " + token
      })
    };
    return this.httpClient.get<any>(`${environment.apiUrl}/login`, httpOptions)
  };
}
