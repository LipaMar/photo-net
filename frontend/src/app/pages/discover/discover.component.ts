import {Component, OnDestroy, OnInit, ViewChild} from '@angular/core';
import {DiscoverService} from "./discover.service";
import {DiscoverDto, DiscoverFilters, SortOption} from "../../core/models/discover.models";
import {Subscription} from "rxjs";
import {DomSanitizer} from "@angular/platform-browser";
import {routes} from "../../core/const/consts";
import {ProfileService} from "../profile/profile.service";
import {SortComponent} from "../../components/sort/sort.component";
import {SortParams} from "../../core/models/basic.models";

@Component({
  selector: 'app-discover',
  templateUrl: './discover.component.html',
  styleUrls: ['./discover.component.scss']
})
export class DiscoverComponent implements OnInit, OnDestroy {

  profiles: DiscoverDto[];
  subscriptions: Subscription[] = [];
  routeToProfile = routes.profile + '/';
  @ViewChild("sortProfiles") sortComponent: SortComponent;

  sortOptions: SortOption[] = [
    {value: "rating", display: "Średniej ocen"},
    {value: "posts", display: "Liczbie zdjęć"},
    {value: "price", display: "Cenie"},
    {value: "ratingCount", display: "Ilości ocen"},
  ]
  categories: string[] = [];
  filters: DiscoverFilters;
  sorting: SortParams;

  constructor(private service: DiscoverService,
              private profileService: ProfileService) {
  }

  ngOnInit(): void {
    this.subscriptions.push(this.service.getList().subscribe(data => {
      this.profiles = data.content;
      this.profiles.forEach(x => x.city = this.firstLetterUpper(x.city))
    }));
    this.subscriptions.push(    this.service.getCategories().subscribe(value => this.categories = value));
  }

  firstLetterUpper(str: string) {
    return this.profileService.firstLetterUpper(str);
  }

  showPic(url: string) {
    return this.profileService.showPic(url);
  }

  sort(event: SortParams){
    this.sorting = event;
    this.sortAndFilter();
  }

  filter(event: DiscoverFilters){
    this.filters = event;
    this.sortAndFilter();
  }

  private sortAndFilter() {
    this.service.getList(this.filters, this.sorting).subscribe(data => {
      this.profiles = data.content;
      this.profiles.forEach(x => x.city = this.firstLetterUpper(x.city))
    })
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach(sub => sub.unsubscribe());
  }
}
