import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {DiscoverDto} from "../../core/models/discover.models";
import {endpoints} from "../../core/const/consts";
import {Pageable} from "../../core/models/pageable";

@Injectable({
  providedIn: 'root'
})
export class DiscoverService {

  constructor(private http: HttpClient) {
  }

  getList(): Observable<Pageable<DiscoverDto>> {
    return this.http.get<Pageable<DiscoverDto>>(endpoints.discover)
  };
}
