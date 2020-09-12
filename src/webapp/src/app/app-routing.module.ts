import {NgModule} from "@angular/core";
import {RouterModule, Routes} from "@angular/router";
import {UserAuthGuard} from "src/app/core/guards/user-auth-guard";
import {HomeComponent} from "src/app/core/home/home.component";
import {LoginComponent} from "src/app/core/login/login.component";

const routes: Routes = [
  {
    path: "",
    component: HomeComponent,
    canActivate: [UserAuthGuard],
  },
  {
    path: "login",
    component: LoginComponent,
    canActivate: [UserAuthGuard],
  },
  {
    path: "home",
    component: HomeComponent,
    canActivate: [UserAuthGuard],
  }
];
@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
