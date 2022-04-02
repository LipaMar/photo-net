import {Component, OnDestroy, OnInit, ViewChild} from '@angular/core';
import {DiscoverService} from "./discover.service";
import {DiscoverDto, DiscoverFilters, SortOption} from "../../core/models/discover.models";
import {Subscription} from "rxjs";
import {routes} from "../../core/const/consts";
import {ProfileService} from "../profile/profile.service";
import {SortComponent} from "../../components/sort/sort.component";
import {SortParams} from "../../core/models/basic.models";
import {CategoryService} from "../../dictionaries/category.service";
import {Comparator} from "../../core/comparator";

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

  categories: string[] = [];
  filters: DiscoverFilters = new DiscoverFilters();
  sorting: SortParams;

  sortingFields: SortOption[] = [
    {
      value: "userName",
      display: "Nazwie użytkownika",
      comparator: Comparator.STRING_COMPARATOR,
      extractor: (a: DiscoverDto) => a.userName
    },
    {
      value: "rating",
      display: "Średniej ocen",
      comparator: Comparator.NUMBER_COMPARATOR,
      extractor: (a: DiscoverDto) => a.rating
    },
    {
      value: "postsCount",
      display: "Liczbie zdjęć",
      comparator: Comparator.NUMBER_COMPARATOR,
      extractor: (a: DiscoverDto) => a.postsCount
    },
    {
      value: "price",
      display: "Cenie",
      comparator: Comparator.NUMBER_COMPARATOR,
      extractor: (a: DiscoverDto) => a.price
    },
    {
      value: "rateCount",
      display: "Ilości ocen",
      comparator: Comparator.NUMBER_COMPARATOR,
      extractor: (a: DiscoverDto) => a.rateCount
    },
  ];

  constructor(private service: DiscoverService,
              private categoryService: CategoryService,
              private profileService: ProfileService) {
  }

  ngOnInit(): void {
    this.service.searchReq.subscribe(value => {
      this.filters.userName = value;
      this.subscriptions.push(this.service.getList(this.filters).subscribe(data => {
        this.profiles = data.content;
        this.profiles.forEach(x => x.city = this.firstLetterUpper(x.city))
      }));
    });

    this.subscriptions.push(this.categoryService.getCategories().subscribe(value => this.categories = value));
  }

  firstLetterUpper(str: string) {
    return str ? this.profileService.firstLetterUpper(str) : "-";
  }

  showPic(url: string) {
    return this.profileService.showPic(url);
  }

  sort(event: SortParams) {
    this.sorting = event;
    this.sortAndFilter();
  }

  filter(event: DiscoverFilters) {
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
