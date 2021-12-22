import {Injectable} from '@angular/core';
import {HttpClient, HttpParams} from "@angular/common/http";
import {Observable} from "rxjs";
import {DiscoverDto, DiscoverFilters} from "../../core/models/discover.models";
import {endpoints} from "../../core/const/consts";
import {Pageable} from "../../core/models/pageable";
import {SortParams} from "../../core/models/basic.models";

@Injectable({
  providedIn: 'root'
})
export class DiscoverService {

  constructor(private http: HttpClient) {
  }

  getList(filters?: DiscoverFilters, sort?: SortParams): Observable<Pageable<DiscoverDto>> {
    return this.http.get<Pageable<DiscoverDto>>(endpoints.discover,{params: this.buildParams(filters, sort)})
  };

  private buildParams(filters: DiscoverFilters | undefined, sort: SortParams | undefined) {
    //Todo: filters
    if (sort) {
      return new HttpParams().append("sort", `${sort?.field},${sort?.order}`);
    }
    return new HttpParams();
  }
}
