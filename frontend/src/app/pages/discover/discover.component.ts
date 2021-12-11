import { Component, OnInit } from '@angular/core';
import {DiscoverService} from "./discover.service";
import {DiscoverDto} from "./discover.models";
import {Subscription} from "rxjs";
import {DomSanitizer} from "@angular/platform-browser";

@Component({
  selector: 'app-discover',
  templateUrl: './discover.component.html',
  styleUrls: ['./discover.component.scss']
})
export class DiscoverComponent implements OnInit {

  profiles: DiscoverDto[];
  subscriptions: Subscription[] = [];
  currentRate = 0;

  constructor(private service: DiscoverService,
              private domSanitizer: DomSanitizer) {}

  ngOnInit(): void {
    this.subscriptions.push(this.service.getList().subscribe(data=>{
      this.profiles = data.content;
    }));
  }

  showPic(url: string){
    return url==null?"assets/images/anon.svg":this.transform(url);
  }

  transform(val: string){
    return this.domSanitizer.bypassSecurityTrustResourceUrl('data:image/jpeg;base64,'+val);
  }

}
