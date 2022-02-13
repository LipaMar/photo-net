import {Injectable} from '@angular/core';
import {HttpClient, HttpParams} from "@angular/common/http";
import {endpoints} from "../core/const/consts";
import {MeetingDto, ScheduleDto} from "../core/models/profile.models";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class ScheduleService {

  constructor(private http: HttpClient) {
  }

  getMeetingInfo(id: number): Observable<MeetingDto> {
    return this.http.get<MeetingDto>(endpoints.meeting, {params: new HttpParams().append("id", id)});
  }

  findMeetingByHour(owner: string, date: string, hour: string) {
    return this.http.get<MeetingDto>(endpoints.meetingByHour, {
      params: new HttpParams().append("owner", owner)
        .append("date", date)
        .append("hour", hour)
    });
  }

  getSchedule(userName: string): Observable<ScheduleDto> {
    return this.http.get<ScheduleDto>(endpoints.schedule, {params:{userName: userName}});
  }

  updateSchedule(schedule: ScheduleDto): Observable<ScheduleDto> {
    return this.http.post<ScheduleDto>(endpoints.schedule, schedule);
  }

  getMyMeetings():Observable<MeetingDto[]> {
    return this.http.get<MeetingDto[]>(endpoints.myMeetings);
  }
}
