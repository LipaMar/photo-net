import {Injectable} from '@angular/core';
import {HttpClient, HttpParams} from "@angular/common/http";
import {BehaviorSubject, Observable} from "rxjs";
import {DiscoverDto, DiscoverFilters} from "../../core/models/discover.models";
import {endpoints} from "../../core/const/consts";
import {Pageable} from "../../core/models/pageable";
import {SortParams} from "../../core/models/basic.models";

@Injectable({
  providedIn: 'root'
})
export class DiscoverService {


  searchReq: BehaviorSubject<any> = new BehaviorSubject<any>("");

  constructor(private http: HttpClient) {
  }

  getList(filters?: DiscoverFilters, sort?: SortParams): Observable<Pageable<DiscoverDto>> {
    return this.http.get<Pageable<DiscoverDto>>(endpoints.discover, {params: this.buildParams(filters, sort)})
  };

  private buildParams(filters: DiscoverFilters | undefined, sort: SortParams | undefined) {
    if (filters && filters.ratingMoreThan <= 0) {
      filters.ratingMoreThan = "";
    }
    if (filters && sort) {
      return new HttpParams().appendAll(<any>filters).append("sort", `${sort?.field},${sort?.order}`);
    } else if (filters) {
      return new HttpParams().appendAll(<any>filters);
    } else if (sort) {
      return new HttpParams().append("sort", `${sort?.field},${sort?.order}`);
    }
    return new HttpParams().append("sort", `userName`);
  }

}
