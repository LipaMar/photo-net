import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {endpoints} from "../core/const/consts";

@Injectable({
  providedIn: 'root'
})
export class CategoryService {

  constructor(private http: HttpClient) {
  }

  getCategories(): Observable<string[]> {
    return this.http.get<string[]>(endpoints.categories);
  };

}
