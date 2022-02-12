import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {BookMeetingDto} from "../../core/models/profile.models";
import {Observable} from "rxjs";
import {endpoints} from "../../core/const/consts";

@Injectable({
  providedIn: 'root'
})
export class OrderService {

  meetingId: number;
  client: string;
  photographer: string;

  constructor(private http: HttpClient) {
  }

  setOrderData(meetingId: number, client: string, photographer: string) {
    this.meetingId = meetingId;
    this.client = client;
    this.photographer = photographer;
  }

  isPopulated() {
    return this.meetingId && this.client && this.photographer;
  }

  bookAMeeting(meeting: BookMeetingDto): Observable<any> {
    return this.http.post(endpoints.book, meeting);
  }

}
