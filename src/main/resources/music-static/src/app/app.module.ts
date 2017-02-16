import {BrowserModule} from "@angular/platform-browser";
import {NgModule} from "@angular/core";
import {FormsModule} from "@angular/forms";
import {HttpModule, XSRFStrategy, CookieXSRFStrategy} from "@angular/http";
import {RouterModule} from "@angular/router";
import {LocalStorageModule} from "angular-2-local-storage";
import {AppComponent} from "./app.component";
import {SigninComponent} from "./outdoor/signin/signin.component";
import {SignupComponent} from "./outdoor/signup/signup.component";
import {HomeComponent} from "./indoor/views/home/home.component";
import {ProfileComponent} from "./indoor/views/profile/profile.component";
import {routes} from "./services/routing";
import {BaseComponent} from "./indoor/views/base/base.component";
import {PlayerComponent} from "./indoor/parts/player/player.component";
import {HeaderComponent} from "./indoor/parts/header/header.component";
import {MenuComponent} from "./indoor/parts/menu/menu.component";
import {SongComponent} from "./indoor/parts/song/song.component";
import {UploadComponent} from "./indoor/views/upload/upload.component";
import {SearchComponent} from "./indoor/views/search/search.component";
import {UserService} from "./services/user.service";
import {GuardService} from "./services/guard.service";
import {AuthService} from "./services/auth.service";
import {DefaultService} from "./services/default.service";
import {PaginationComponent} from "./indoor/parts/pagination/pagination.component";
import { SettingsComponent } from './indoor/views/settings/settings.component';
import { ActiveComponent } from './outdoor/active/active.component';
import { TabComponent } from './indoor/parts/tab/tab.component';
export function Cookie() {
  return new CookieXSRFStrategy('XSRF-TOKEN', 'X-XSRF-TOKEN')
}

@NgModule({
  declarations: [
    AppComponent,
    SigninComponent,
    SignupComponent,
    HomeComponent,
    ProfileComponent,
    BaseComponent,
    PlayerComponent,
    HeaderComponent,
    MenuComponent,
    SongComponent,
    UploadComponent,
    SearchComponent,
    PaginationComponent,
    SettingsComponent,
    ActiveComponent,
    TabComponent],
  imports: [LocalStorageModule.withConfig({
    prefix: 'my-app',
    storageType: 'localStorage'
  }),
    BrowserModule,
    FormsModule,
    HttpModule, RouterModule.forRoot(routes)
  ],
  providers: [UserService, GuardService, AuthService, {
    provide: XSRFStrategy,
    useFactory: Cookie
  }, DefaultService],
  bootstrap: [AppComponent]
})
export class AppModule {
}
