import {Component, ElementRef, OnDestroy, OnInit, ViewChild} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {ProfileService} from "./profile.service";
import {CommentDto, MeetingDto, ProfileDto, ProfileUpdateDto, ScheduleDto} from "../../core/models/profile.models";
import {DatePipe} from "@angular/common";
import {AppToastrService} from "../../core/toastr.service";
import {SubscriptionContainer} from "../../core/utils/subscription-container";
import {LoginService} from "../../login/login.service";
import {FormControl, FormGroup} from "@angular/forms";
import {CategoryService} from "../../dictionaries/category.service";
import {ChipsSelectComponent} from "../../components/chips-select/chips-select.component";

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.scss'],
  providers: [DatePipe]
})
export class ProfileComponent implements OnInit, OnDestroy {

  profile: ProfileDto;
  allCategories: string[] = [];
  backupCategories: string[] = [];
  @ViewChild('followBtn') followBtn: ElementRef;
  @ViewChild('categorySelect') categorySelect: ChipsSelectComponent;
  userName = this.route.snapshot.paramMap.get('username');
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

  constructor(private route: ActivatedRoute,
              private profileService: ProfileService,
              private loginService: LoginService,
              private categoryService: CategoryService,
              private toastr: AppToastrService) {
  }

  ngOnInit(): void {
    if (this.userName) {
      this.getProfileDetails();
    } else {
      this.getMyProfileDetails();
    }
    this.subscriptions.add = this.categoryService.getCategories().subscribe(categories => {
      this.allCategories = categories;
    });
  }

  private getProfileDetails() {
    this.subscriptions.add = this.profileService.getProfileDetails(this.userName).subscribe(data => this.profile = data);
    this.subscriptions.add = this.profileService.isFollowed(this.userName).subscribe(bool => this.doIsFollowing(bool));
    this.getSchedule(this.userName);
  }

  private getMyProfileDetails() {
    this.subscriptions.add = this.loginService.doGetIsLogged().subscribe(data => {
      if (data && data.active) {
        this.userName = data.userName;
        this.isMyProfile = true;
        this.subscriptions.add = this.profileService.getProfileDetails(this.userName).subscribe(data => {
          this.profile = data;
          this.setFormFieldsFromProfile();
          this.profileUpdateForm.disable();
        });
        this.getSchedule(this.userName);
      }
    })
  }

  private getSchedule(userName: string | any) {
    this.subscriptions.add = this.profileService.getSchedule(userName).subscribe(schedule => {
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
    if (!this.userName) {
      return;
    }
    if (this.isFollowing) {
      this.doIsFollowing(!this.isFollowing);
      this.profileService.unfollow(this.userName).subscribe();
    } else {
      this.doIsFollowing(!this.isFollowing);
      this.profileService.follow(this.userName).subscribe();
    }
  }

  addComment(comment: CommentDto) {
    if (this.userName) {
      comment.target = this.userName;
      this.subscriptions.add = this.profileService.addComment(comment).subscribe(() => {
          this.toastr.success('message.addComment.success');
          this.subscriptions.add = this.profileService.getProfileDetails(this.userName).subscribe(data => this.profile = data);

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
    if (this.userName) {
      const dto: ScheduleDto = {
        owner: this.userName,
        disabled: false,
        meetings: event.meetings,
        saveDate: event.saveDate
      };
      this.subscriptions.add = this.profileService.updateSchedule(dto).subscribe(() => this.getSchedule(this.userName));
    }
  }
}
