import {Component, OnInit} from "@angular/core";
import {User} from "../../domain/User";
import {UserService} from "../../services/user.service";
import {Router, ActivatedRoute} from "@angular/router";

@Component({
  selector: 'm-signin',
  templateUrl: './signin.component.html',
  styleUrls: ['./signin.component.css']
})
export class SigninComponent implements OnInit {
  user: User = new User();
  message: any = null;

  constructor(private _userService: UserService, private _activatedRoute: ActivatedRoute, private _router: Router) {
  }

  ngOnInit() {
  }

  login() {

    this._userService.login(this.user, "signin", (value: string): void => {
      this.message = value;
    })
  }
}
