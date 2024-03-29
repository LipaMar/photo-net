import {Injectable} from '@angular/core';
import {HttpClient, HttpParams} from "@angular/common/http";
import {endpoints} from "../core/const/consts";
import {MeetingDto, MeetingStatus, ScheduleDto} from "../core/models/profile.models";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class ScheduleService {

  constructor(private http: HttpClient) {
  }

  getMeetingInfo(id: number): Observable<MeetingDto> {
    return this.http.get<MeetingDto>(`${endpoints.meeting}/${id}`);
  }

  updateMeetingStatus(id: number, status: MeetingStatus): Observable<MeetingDto> {
    return this.http.put<MeetingDto>(`${endpoints.meeting}/${id}`, null,{params: new HttpParams().append("status", status)});
  }

  findMeetingByHour(owner: string, date: string, hour: string) {
    return this.http.get<MeetingDto>(endpoints.meetingByHour, {
      params: new HttpParams().append("owner", owner)
        .append("date", date)
        .append("hour", hour)
    });
  }

  getSchedule(userName: string): Observable<ScheduleDto> {
    return this.http.get<ScheduleDto>(endpoints.schedule, {params: {userName: userName}});
  }

  updateSchedule(schedule: ScheduleDto): Observable<ScheduleDto> {
    return this.http.post<ScheduleDto>(endpoints.schedule, schedule);
  }

  getMyMeetings(): Observable<MeetingDto[]> {
    return this.http.get<MeetingDto[]>(endpoints.myMeetings);
  }

  rateMeeting(id: number, rating: number) {
    return this.http.post(`${endpoints.meeting}/${id}`,rating);
  }
}
