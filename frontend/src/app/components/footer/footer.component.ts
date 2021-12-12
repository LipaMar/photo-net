import { Component, OnInit } from '@angular/core';
import {routes} from "../../core/const/consts";

@Component({
  selector: 'app-footer',
  templateUrl: './footer.component.html',
  styleUrls: ['./footer.component.scss']
})
export class FooterComponent implements OnInit {

  instagramRef = routes.instagram;
  facebookRef = routes.facebook;
  twitterRef = routes.twitter;

  constructor() { }

  ngOnInit(): void {
  }

}
