import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {HomeComponent} from "./pages/home/home.component";
import {NotFoundComponent} from "./pages/not-found/not-found.component";
import {RegisterComponent} from "./pages/register/register.component";
import {DiscoverComponent} from "./pages/discover/discover.component";

const routes: Routes = [
  {path: "home", component: HomeComponent},
  {path: "", redirectTo: "/home", pathMatch: "full"},
  {path: "register", component: RegisterComponent},
  {path: "discover", component: DiscoverComponent},
  {path: "**", component: NotFoundComponent},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
