import {Routes} from "@angular/router";
import {SigninComponent} from "../outdoor/signin/signin.component";
import {SignupComponent} from "../outdoor/signup/signup.component";
import {BaseComponent} from "../indoor/views/base/base.component";
import {HomeComponent} from "../indoor/views/home/home.component";
import {ProfileComponent} from "../indoor/views/profile/profile.component";
import {UploadComponent} from "../indoor/views/upload/upload.component";
import {SearchComponent} from "../indoor/views/search/search.component";
import {GuardService} from "./guard.service";
import {SettingsComponent} from "../indoor/views/settings/settings.component";
import {ActiveComponent} from "../outdoor/active/active.component";
export const routes: Routes = [
  {path: '', component: SigninComponent, canActivate: [GuardService]},
  {path: 'signin', component: SigninComponent, canActivate: [GuardService]},
  {path: 'signup', component: SignupComponent, canActivate: [GuardService]},
  {path: 'active/:id', component: ActiveComponent, canActivate: [GuardService]},
  {
    path: '', component: BaseComponent, children: [
    {path: 'home', component: HomeComponent, canActivate: [GuardService]},
    {path: 'profile/:id', component: ProfileComponent, canActivate: [GuardService]},
    {path: 'settings', component: SettingsComponent, canActivate: [GuardService]},
    {path: 'upload', component: UploadComponent, canActivate: [GuardService]},
    {path: 'search/:search', component: SearchComponent, canActivate: [GuardService]}
  ]
  },
];
