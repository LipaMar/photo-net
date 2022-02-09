import { Component, OnInit } from '@angular/core';
import {OrderService} from "./order.service";
import {Router} from "@angular/router";
import {routes} from "../../core/const/consts";
import {DatePipe} from "@angular/common";
import {ProfileService} from "../profile/profile.service";
import {SubscriptionContainer} from "../../core/utils/subscription-container";

@Component({
  selector: 'app-order',
  templateUrl: './order.component.html',
  styleUrls: ['./order.component.scss']
})
export class OrderComponent implements OnInit {

  date: Date;
  dateToDisplay: string | null;
  hour: string;
  client: string;
  photographer: string;
  price: number;
  place: string;

  subscriptions = new SubscriptionContainer();

  constructor(private orderService: OrderService,
              private profileService: ProfileService,
              private datePipe: DatePipe,
              private router: Router) {
    if(!this.orderService.isPopulated()){
      this.router.navigateByUrl(routes.home);
    }
    this.date = orderService.date;
    this.hour = orderService.hour;
    this.client = orderService.client;
    this.photographer = orderService.photographer;
  }

  ngOnInit(): void {
    this.dateToDisplay = this.datePipe.transform(this.date, 'EEEE, d MMMM y', undefined, 'pl-PL');
    this.subscriptions.add = this.profileService.getProfileDetails(this.photographer).subscribe(photographer => {
      this.price = photographer.price;
      this.place = photographer.city;
    });
  }

}
