import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {HomeComponent} from "./pages/home/home.component";
import {NotFoundComponent} from "./pages/not-found/not-found.component";
import {RegisterComponent} from "./pages/register/register.component";
import {DiscoverComponent} from "./pages/discover/discover.component";
import {routes} from "./core/const/consts";
import {ProfileComponent} from "./pages/profile/profile.component";
import {IsLoggedGuard} from "./guards/is-logged.guard";
import {OrderComponent} from "./pages/order/order.component";

const appRoutes: Routes = [
  {path: "home", component: HomeComponent},
  {path: "", redirectTo: routes.home, pathMatch: "full"},
  {path: "register", component: RegisterComponent},
  {path: "discover", canActivate: [IsLoggedGuard], component: DiscoverComponent},
  {path: "profile/:username", canActivate: [IsLoggedGuard], component: ProfileComponent},
  {path: "my-profile", canActivate: [IsLoggedGuard], component: ProfileComponent},
  {path: "confirm-meeting", canActivate: [IsLoggedGuard], component: OrderComponent},
  {path: "**", component: NotFoundComponent},
];

@NgModule({
  imports: [RouterModule.forRoot(appRoutes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
