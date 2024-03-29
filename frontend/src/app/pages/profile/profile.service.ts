import {Injectable} from '@angular/core';
import {HttpClient, HttpParams} from "@angular/common/http";
import {Observable} from "rxjs";
import {CommentDto, ProfileDto, ProfileUpdateDto} from "../../core/models/profile.models";
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

  getProfileSimple(username: any): Observable<ProfileDto> {
    return this.http.get<ProfileDto>(`${endpoints.profile}/${username}`, {params: new HttpParams().append("simple", true)});
  }

  isFollowed(userName: string | any): Observable<boolean> {
    return this.http.get<boolean>(endpoints.follow, {params: this.buildParams(userName)});
  }

  follow(userName: string): Observable<void> {
    return this.http.post<void>(endpoints.follow, this.buildParams(userName));
  }

  unfollow(userName: string): Observable<void> {
    return this.http.delete<void>(endpoints.follow, {params: this.buildParams(userName)});
  }

  private buildParams(userName: string) {
    return new HttpParams().append('userName', userName);
  }

  addComment(comment: CommentDto) {
    return this.http.post<CommentDto>(endpoints.comment, comment);
  }

  firstLetterUpper(str: string) {
    return str.charAt(0).toUpperCase() + str.slice(1);
  }

  showPic(url: string) {
    return url == null ? "assets/images/anon.svg" : url;
  }

  transform(val: string) {
    return this.domSanitizer.bypassSecurityTrustResourceUrl('data:image/jpeg;base64,' + val);
  }

  uploadProfilePicture(picture: any) {
    const fd = new FormData();
    fd.append('file', picture);
    return this.http.post(endpoints.myProfile, fd);
  }

  updateProfile(data: ProfileUpdateDto): Observable<ProfileDto> {
    return this.http.put<ProfileDto>(endpoints.myProfile, data);
  }

}
