import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {ProfileDto} from "../../core/models/profile.models";
import {endpoints} from "../../core/const/consts";

@Injectable({
  providedIn: 'root'
})
export class ProfileService {

  constructor(private http: HttpClient) {
  }

  getProfileDetails(username: string): Observable<ProfileDto> {
    return this.http.get<ProfileDto>(`${endpoints.profile}/${username}`);

  }

}
