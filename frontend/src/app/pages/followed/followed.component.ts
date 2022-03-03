import {Component, OnDestroy, OnInit, ViewChild, ViewEncapsulation} from '@angular/core';
import {routes} from "../../core/const/consts";
import {PostDisplay, PostImage} from "../../core/models/profile.models";
import {PostService} from "../../services/post.service";
import {SubscriptionContainer} from "../../core/utils/subscription-container";
import {DatePipe} from "@angular/common";
import {DatePattern} from "../../core/enums/datePattern";
import {ProfileService} from "../profile/profile.service";
import {NgxMasonryOptions} from "ngx-masonry";
import {IMasonryGalleryImage} from "ngx-masonry-gallery";
import {ImageUtils} from "../../core/utils/ImageUtils";
import {FullScreenModalComponent} from "../../components/full-screen-modal/full-screen-modal.component";

@Component({
  selector: 'app-followed',
  templateUrl: './followed.component.html',
  styleUrls: ['./followed.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class FollowedComponent implements OnInit, OnDestroy {
  routeToDiscover = routes.discover;
  routeToProfile = routes.profile + '/';
  posts: PostDisplay[];
  images: IMasonryGalleryImage[];
  subscriptions = new SubscriptionContainer();
  profilePics = new Map<string, any>();
  galleryOptions: NgxMasonryOptions = {columnWidth: 2};
  @ViewChild("modal") modal: FullScreenModalComponent;

  constructor(private postService: PostService,
              private profileService: ProfileService,
              private datePipe: DatePipe) {
  }

  ngOnInit(): void {
    this.subscriptions.add = this.postService.getPostsOfFollowedUsers().subscribe(posts => {
      this.posts = posts;
      this.images = ImageUtils.urlsToIMasonryGalleryImage(this.posts.map(post=>post.photo));
      this.posts.map(post => post.timestamp = this.transform(post.timestamp));
      this.posts.map(post => post.author).filter((value, index, self) => self.indexOf(value) === index).forEach(author => {
        this.profilePics.set(author, null);
        this.subscriptions.add = this.profileService.getProfileSimple(author).subscribe(profile => {
          this.profilePics.set(profile.userName, profile.profilePicture);
        });
      });
      this.subscriptions.add = this.postService.getLiked().subscribe(liked => this.setLikedPropertyForAllPosts(liked));
    });
  }

  private setLikedPropertyForAllPosts(ids: number[]) {
    this.posts.forEach(post => {
      post.liked = ids.indexOf(post.id) >= 0;
    });
  }

  private transform(timestamp: string) {
    const transform = this.datePipe.transform(timestamp, DatePattern.TIME_DATE_DOTS);
    return transform ? transform : "";
  }

  ngOnDestroy(): void {
    this.subscriptions.dispose();
  }

  showProfilePic(author: string) {
    const url = this.profilePics.get(author);
    return url == null ? "assets/images/anon.svg" : url;
  }

  styleLikeButtons(likeButton: HTMLButtonElement, post: PostDisplay) {
    const item = likeButton.children.item(0);
    if (item) {
      if (post.liked) {
        item.className = "fas fa-thumbs-up";
        return `btn btn-dark-brown`;
      } else {
        item.className = "far fa-thumbs-up";
        return `btn btn-outline-dark-brown`;
      }
    }
    return "btn btn-light";
  }

  photoClicked(post:PostImage){
    this.modal.openModal(post.imageUrl);
  }

  likeClicked(post: PostDisplay) {
    post.liked = !post.liked;
    this.subscriptions.add = this.postService.like(post.id).subscribe();
  }
}
