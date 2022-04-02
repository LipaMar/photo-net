import {Component, ElementRef, OnDestroy, OnInit, ViewChild, ViewEncapsulation} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {ProfileService} from "./profile.service";
import {
  CommentDto,
  MeetingDto,
  PostImage,
  ProfileDto,
  ProfileUpdateDto,
  ScheduleDto
} from "../../core/models/profile.models";
import {DatePipe} from "@angular/common";
import {AppToastrService} from "../../core/toastr.service";
import {SubscriptionContainer} from "../../core/utils/subscription-container";
import {LoginService} from "../../login/login.service";
import {FormControl, FormGroup} from "@angular/forms";
import {CategoryService} from "../../dictionaries/category.service";
import {ChipsSelectComponent} from "../../components/chips-select/chips-select.component";
import {OrderService} from "../order/order.service";
import {ScheduleService} from "../../services/schedule.service";
import {PostService} from "../../services/post.service";
import {ImageUtils} from "../../core/utils/ImageUtils";
import {FullScreenModalComponent} from "../../components/full-screen-modal/full-screen-modal.component";
import {routes} from "../../core/const/consts";
import {MessagesService} from "../messages/messages.service";

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.scss'],
  providers: [DatePipe],
  encapsulation: ViewEncapsulation.None
})
export class ProfileComponent implements OnInit, OnDestroy {

  profile: ProfileDto;
  allCategories: string[] = [];
  backupCategories: string[] = [];
  @ViewChild('followBtn') followBtn: ElementRef;
  @ViewChild('categorySelect') categorySelect: ChipsSelectComponent;
  pathUserName = this.route.snapshot.paramMap.get('username');
  loggedUserName = '';
  subscriptions = new SubscriptionContainer();
  profileUpdateForm = new FormGroup({
    bio: new FormControl({disabled: true}),
    city: new FormControl({disabled: true}),
    price: new FormControl({disabled: true})
  });
  isFollowing: boolean = false;

  isMyProfile: boolean = false;
  inEditMode: boolean = false;
  schedule: ScheduleDto;

  @ViewChild('modal') modal: FullScreenModalComponent;
  modalPhoto: PostImage;

  images: PostImage[];

  constructor(private route: ActivatedRoute,
              private profileService: ProfileService,
              private router: Router,
              private orderService: OrderService,
              private scheduleService: ScheduleService,
              private loginService: LoginService,
              private postService: PostService,
              private messageService: MessagesService,
              private categoryService: CategoryService,
              private toastr: AppToastrService) {
  }

  ngOnInit(): void {
    this.subscriptions.add = this.loginService.doGetIsLogged().subscribe(data => {
      this.loggedUserName = data.userName;
      this.getUserDetails();
    });
    this.subscriptions.add = this.categoryService.getCategories().subscribe(categories => {
      this.allCategories = categories;
    });
  }

  private getUserDetails() {
    if (!(this.pathUserName && this.pathUserName !== this.loggedUserName)) {
      this.getMyProfileDetails();
    } else {
      this.getProfileDetails();
    }
  }

  private getProfileDetails() {
    this.subscriptions.add = this.profileService.getProfileDetails(this.pathUserName).subscribe(data => {
      this.profile = data;
      this.images = ImageUtils.postsToPostImage(this.profile.posts);
      this.sortPosts();
    });
    this.subscriptions.add = this.profileService.isFollowed(this.pathUserName).subscribe(bool => this.doIsFollowing(bool));
    this.getSchedule(this.pathUserName);
  }

  private getMyProfileDetails() {
    this.subscriptions.add = this.loginService.doGetIsLogged().subscribe(data => {
      if (data && data.active) {
        this.pathUserName = data.userName;
        this.isMyProfile = true;
        this.subscriptions.add = this.profileService.getProfileDetails(this.pathUserName).subscribe(data => {
          this.profile = data;
          this.images = ImageUtils.postsToPostImage(this.profile.posts);
          this.setFormFieldsFromProfile();
          this.profileUpdateForm.disable();
          this.sortPosts();
        });
        this.getSchedule(this.pathUserName);
      }
    })
  }

  private sortPosts() {
    if (this.profile && this.profile.posts) {
      this.profile.posts = this.profile.posts.sort((a, b) => new Date(a.timestamp) < new Date(b.timestamp) ? 1 : -1);
    }
  }

  private getSchedule(userName: string | any) {
    this.subscriptions.add = this.scheduleService.getSchedule(userName).subscribe(schedule => {
      this.schedule = schedule;
    });
  }

  private doIsFollowing(bool: boolean) {
    this.isFollowing = bool;
    this.styleFollowBtn(bool);
  }

  showPic(url: string) {
    return this.profileService.showPic(url);
  }

  ngOnDestroy(): void {
    this.subscriptions.dispose();
  }

  styleFollowBtn(bool: boolean) {
    if (bool) {
      this.followBtn.nativeElement.innerHTML = "Nie obserwuj";
      this.followBtn.nativeElement.className = "mx-2 btn btn-dark-brown";
    } else {
      this.followBtn.nativeElement.innerHTML = "Obserwuj";
      this.followBtn.nativeElement.className = "mx-2 btn btn-brown";
    }
  }

  followOnClick() {
    if (!this.pathUserName) {
      return;
    }
    if (this.isFollowing) {
      this.doIsFollowing(!this.isFollowing);
      this.profileService.unfollow(this.pathUserName).subscribe();
      this.profile.observers--;
    } else {
      this.doIsFollowing(!this.isFollowing);
      this.profileService.follow(this.pathUserName).subscribe();
      this.profile.observers++;
    }
  }

  addComment(comment: CommentDto) {
    if (this.pathUserName) {
      comment.target = this.pathUserName;
      this.subscriptions.add = this.profileService.addComment(comment).subscribe(() => {
          this.toastr.success('message.addComment.success');
          this.subscriptions.add = this.profileService.getProfileDetails(this.pathUserName).subscribe(data => this.profile = data);

        },
        () => this.toastr.error("message.addComment.failure"));
    }
  }

  onFileSelected(input: any) {
    if (input.target.files && input.target.files[0]) {
      let reader = new FileReader();

      reader.readAsDataURL(input.target.files[0]);

      this.subscriptions.add = this.profileService.uploadProfilePicture(input.target.files[0]).subscribe();
      reader.onload = (event) => this.profile.profilePicture = event.target?.result;
    }
  }

  onEditClicked() {
    this.profileUpdateForm.enable();
    this.inEditMode = !this.inEditMode;
    this.backupCategories = this.profile.categories;
  }

  onCancelClicked() {
    this.setFormFieldsFromProfile();
    this.profileUpdateForm.disable();
    this.inEditMode = !this.inEditMode;
    this.profile.categories = this.backupCategories;
    this.categorySelect.resetTo(this.backupCategories);
  }

  onSaveClicked() {
    this.profileUpdateForm.disable();
    this.inEditMode = !this.inEditMode;
    let updated = {
      userName: this.profile.userName,
      bio: this.profileUpdateForm.get("bio")?.value,
      city: this.profileUpdateForm.get("city")?.value,
      price: this.profileUpdateForm.get("price")?.value,
      categories: this.profile.categories
    } as ProfileUpdateDto;
    this.subscriptions.add = this.profileService.updateProfile(updated).subscribe(response => {
      this.profile = response;
    })
  }

  private setFormFieldsFromProfile() {
    this.profileUpdateForm.setValue({
      bio: this.profile.bio,
      city: this.profile.city,
      price: this.profile.price
    });
  }

  onCategorySelected(selectedCategories: string[]) {
    this.profile.categories = selectedCategories;
  }

  onSaveSchedule(event: { meetings: MeetingDto[], saveDate: any }) {
    if (this.pathUserName) {
      const dto: ScheduleDto = {
        owner: this.pathUserName,
        disabled: false,
        meetings: event.meetings,
        saveDate: event.saveDate
      };
      this.subscriptions.add = this.scheduleService.updateSchedule(dto).subscribe(() => this.getSchedule(this.pathUserName));
    }
  }

  goToOrderConfirm(meetingInfo: { date: string; hour: string }) {
    if (this.pathUserName) {
      this.scheduleService.findMeetingByHour(this.pathUserName, meetingInfo.date, meetingInfo.hour).subscribe(meeting => {
        if (meeting.id && this.pathUserName) {
          this.orderService.setOrderData(meeting.id, this.loggedUserName, this.pathUserName);
          this.router.navigate(['confirm-meeting']);
        }
      });
    }
  }

  onPostFileSelected(input: any) {
    if (input.target.files && input.target.files[0]) {
      let reader = new FileReader();

      reader.readAsDataURL(input.target.files[0]);

      this.subscriptions.add = this.postService.createNewPost(input.target.files[0]).subscribe(() => this.getUserDetails());
    }
  }

  onPicClicked(photo: PostImage) {
    this.modal.openModal(photo.imageUrl);
    this.modalPhoto = photo;
  }

  deletePost(postId: any) {
    this.subscriptions.add = this.postService.deletePost(postId).subscribe(() => this.getUserDetails());
    this.modal.closeModal();
  }

  messageToClicked() {
    if (this.pathUserName) {
      this.subscriptions.add = this.messageService.startNewChat(this.pathUserName).subscribe(() => this.router.navigateByUrl(routes.messages));
    }
  }
}
