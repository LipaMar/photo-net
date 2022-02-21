import { Injectable } from '@angular/core';
import {HttpClient, HttpParams} from "@angular/common/http";
import {endpoints} from "../core/const/consts";

@Injectable({
  providedIn: 'root'
})
export class PostService {

  constructor(private http:HttpClient) { }

  createNewPost(photo: any) {
    const fd = new FormData();
    fd.append('file', photo);
    return this.http.post(endpoints.post, fd);
  }

  deletePost(id: number) {
    return this.http.delete(endpoints.post, {params:new HttpParams().append("id", id)});
  }
}
