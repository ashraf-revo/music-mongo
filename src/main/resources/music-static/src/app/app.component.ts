import {Component, OnInit} from "@angular/core";
import {Router} from "@angular/router";
import {UserService} from "./services/user.service";
import {DefaultService} from "./services/default.service";

@Component({
  selector: 'm-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  constructor(private _userService: UserService, private _router: Router, private _defaultService: DefaultService) {

  }

  ngOnInit() {
    this._defaultService.urlListener(this._router.events);
    this._userService.loginRoute(this._userService.user(), (value: string): void => {
      console.log("from fun " + value);
    });
  }
}
