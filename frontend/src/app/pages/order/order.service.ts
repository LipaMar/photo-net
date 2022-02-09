import {Injectable} from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class OrderService {

  date: Date;
  hour: string;
  client: string;
  photographer: string;

  constructor() {
  }

  setOrderData(date: Date, hour: string, client: string, photographer: string) {
    this.date = date;
    this.hour = hour;
    this.client = client;
    this.photographer = photographer;
  }

  isPopulated() {
    return this.date && this.hour && this.client && this.photographer;
  }

}
