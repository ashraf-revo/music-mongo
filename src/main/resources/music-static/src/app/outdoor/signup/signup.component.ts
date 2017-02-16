import {Component, OnInit} from "@angular/core";
import {UserService} from "../../services/user.service";
import {User} from "../../domain/User";

@Component({
  selector: 'm-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css']
})
export class SignupComponent implements OnInit {
  user: User = new User();
  message: any = null;

  constructor(private _userService: UserService) {
  }

  ngOnInit() {
  }

  save() {
    this._userService.save(this.user).subscribe(user => {
    }, error => {
      this.message = error._body;
    })
  }
}
