import {Component, ElementRef, OnDestroy, OnInit, ViewChild} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {ProfileService} from "./profile.service";
import {Subscription} from "rxjs";
import {CommentDto, ProfileDto} from "../../core/models/profile.models";
import {DatePipe} from "@angular/common";
import {AppToastrService} from "../../core/toastr.service";

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.scss'],
  providers: [DatePipe]
})
export class ProfileComponent implements OnInit, OnDestroy {

  subscriptions: Subscription[] = [];
  profile: ProfileDto;
  @ViewChild('followBtn') followBtn: ElementRef;
  private userName = this.route.snapshot.paramMap.get('username');

  isFollowing: boolean = false;

  constructor(private route: ActivatedRoute,
              private service: ProfileService,
              private toastr: AppToastrService) {
  }

  ngOnInit(): void {
    this.subscriptions.push(this.service.getProfileDetails(this.userName).subscribe(data => {
      this.profile = data;
    }));
    this.subscriptions.push(this.service.isFollowed(this.userName).subscribe(bool => this.doIsFollowing(bool)));
  }

  private doIsFollowing(bool: boolean) {
    this.isFollowing = bool;
    this.styleFollowBtn(bool);
  }

  showPic(url: string) {
    return this.service.showPic(url);
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach(sub => sub.unsubscribe());
  }

  styleFollowBtn(bool: boolean) {
    if (bool) {
      this.followBtn.nativeElement.innerHTML =  "Nie obserwuj";
      this.followBtn.nativeElement.className =  "mx-2 btn btn-dark-brown";
    } else {
      this.followBtn.nativeElement.innerHTML =  "Obserwuj";
      this.followBtn.nativeElement.className = "mx-2 btn btn-brown" ;
    }
  }

  followOnClick() {
    if(!this.userName){
      return;
    }
    if (this.isFollowing) {
      this.doIsFollowing(!this.isFollowing);
      this.service.unfollow(this.userName).subscribe();
    } else {
      this.doIsFollowing(!this.isFollowing);
      this.service.follow(this.userName).subscribe();
    }
  }

  addComment(comment: CommentDto){
    if(this.userName){
      comment.target = this.userName;
      this.service.addComment(comment).subscribe(response=> {
        this.toastr.success('message.addComment.success');
      },
        ()=>this.toastr.error("message.addComment.failure"));
    }
  }
}
