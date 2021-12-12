import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {HomeComponent} from "./pages/home/home.component";
import {NotFoundComponent} from "./pages/not-found/not-found.component";
import {RegisterComponent} from "./pages/register/register.component";
import {DiscoverComponent} from "./pages/discover/discover.component";
import {routes} from "./core/const/consts";
import {ProfileComponent} from "./pages/profile/profile.component";

const appRoutes: Routes = [
  {path: "home", component: HomeComponent},
  {path: "", redirectTo: routes.home, pathMatch: "full"},
  {path: "register", component: RegisterComponent},
  {path: "discover", component: DiscoverComponent},
  {path: "profile/:username", component: ProfileComponent},
  {path: "**", component: NotFoundComponent},
];

@NgModule({
  imports: [RouterModule.forRoot(appRoutes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
