import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {ProfileDto} from "../../core/models/profile.models";
import {endpoints} from "../../core/const/consts";
import {DomSanitizer} from "@angular/platform-browser";

@Injectable({
  providedIn: 'root'
})
export class ProfileService {

  constructor(private http: HttpClient,
              private domSanitizer: DomSanitizer) {
  }

  getProfileDetails(username: any): Observable<ProfileDto> {
    return this.http.get<ProfileDto>(`${endpoints.profile}/${username}`);
  }

  firstLetterUpper(str: string) {
    return str.charAt(0).toUpperCase() + str.slice(1);
  }

  showPic(url: string) {
    return url == null ? "assets/images/anon.svg" : this.transform(url);
  }

  transform(val: string) {
    return this.domSanitizer.bypassSecurityTrustResourceUrl('data:image/jpeg;base64,' + val);
  }
}
