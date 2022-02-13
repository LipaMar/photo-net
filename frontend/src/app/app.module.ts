import {LOCALE_ID, NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {NavbarComponent} from './components/navbar/navbar.component';
import {HomeComponent} from './pages/home/home.component';
import {LoginComponent} from './login/login.component';
import {NotFoundComponent} from './pages/not-found/not-found.component';
import {FooterComponent} from "./components/footer/footer.component";
import {RegisterComponent} from './pages/register/register.component';
import {HTTP_INTERCEPTORS, HttpClient, HttpClientModule} from "@angular/common/http";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {ToastrModule} from "ngx-toastr";
import {TranslateLoader, TranslateModule} from "@ngx-translate/core";
import {TranslateHttpLoader} from "@ngx-translate/http-loader";
import {LoginInterceptor} from "./interceptors/login.interceptor";
import {ModalComponent} from './components/modal/modal.component';
import {NgbCollapseModule, NgbModule} from "@ng-bootstrap/ng-bootstrap";
import {DiscoverComponent} from './pages/discover/discover.component';
import {SortComponent} from './components/sort/sort.component';
import {MaterialModule} from "../material.module";
import {ProfileComponent} from './pages/profile/profile.component';
import {FilterComponent} from './components/filter/filter.component';
import {StarRatingComponent} from './components/star-rating/star-rating.component';
import {CommentSectionComponent} from './components/comment-section/comment-section.component';
import {IsLoggedGuard} from "./guards/is-logged.guard";
import {ChipsSelectComponent} from './components/chips-select/chips-select.component';
import {CalendarComponent} from './components/calendar/calendar.component';
import {MAT_DATE_LOCALE} from "@angular/material/core";
import '@angular/common/locales/global/pl';
import {TimePickerComponent} from './components/time-picker/time-picker.component';
import {OrderComponent} from './pages/order/order.component';
import {DatePipe} from "@angular/common";
import {MyMeetingsComponent} from './pages/my-meetings/my-meetings.component';
import { MeetingListFilterComponent } from './components/list-filter/meeting-list-filter.component';

@NgModule({
  declarations: [
    AppComponent,
    NavbarComponent,
    HomeComponent,
    LoginComponent,
    NotFoundComponent,
    FooterComponent,
    RegisterComponent,
    ModalComponent,
    DiscoverComponent,
    SortComponent,
    ProfileComponent,
    FilterComponent,
    StarRatingComponent,
    CommentSectionComponent,
    ChipsSelectComponent,
    CalendarComponent,
    TimePickerComponent,
    OrderComponent,
    MyMeetingsComponent,
    MeetingListFilterComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
    BrowserAnimationsModule,
    TranslateModule.forRoot({
      loader: {
        provide: TranslateLoader,
        useFactory: HttpLoaderFactory,
        deps: [HttpClient]
      }
    }),
    ToastrModule.forRoot(),
    NgbCollapseModule,
    MaterialModule,
    NgbModule
  ],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: LoginInterceptor,
      multi: true,
    },
    {
      provide: MAT_DATE_LOCALE, useValue: 'pl-PL'
    },
    {provide: LOCALE_ID, useValue: 'pl-PL'},
    IsLoggedGuard,
    DatePipe
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}

export function HttpLoaderFactory(http: HttpClient): TranslateHttpLoader {
  return new TranslateHttpLoader(http);
}
