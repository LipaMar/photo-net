import {Injectable} from '@angular/core';
import {HttpClient, HttpParams} from "@angular/common/http";
import {endpoints} from "../core/const/consts";
import {PostDto} from "../core/models/profile.models";

@Injectable({
  providedIn: 'root'
})
export class PostService {

  constructor(private http: HttpClient) {
  }

  createNewPost(photo: any) {
    const fd = new FormData();
    fd.append('file', photo);
    return this.http.post(endpoints.post, fd);
  }

  deletePost(id: number) {
    return this.http.delete(endpoints.post, this.buildParameters(id));
  }

  getPostsOfFollowedUsers() {
    return this.http.get<PostDto[]>(endpoints.post);
  }

  like(id: number) {
    return this.http.post(endpoints.like, null, this.buildParameters(id));
  }

  getLiked() {
    return this.http.get<number[]>(endpoints.like);
  }

  private buildParameters(id: number) {
    return {params: new HttpParams().append("id", id)};
  }

}
