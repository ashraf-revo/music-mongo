import {Component, OnInit} from "@angular/core";
import {UserService} from "../../../services/user.service";
import {AuthService} from "../../../services/auth.service";
import {User} from "../../../domain/User";

@Component({
  selector: 'm-settings',
  templateUrl: './settings.component.html',
  styleUrls: ['./settings.component.css']
})
export class SettingsComponent implements OnInit {
  user: User = new User();

  constructor(private _userService: UserService, private _authService: AuthService) {
  }

  ngOnInit() {
    this.user = this._authService.getAuth()
  }

  name(ref1: any, ref2: any) {
    if (ref1.files.length == 1) {
      ref2.value = ref1.files[0].name;
    } else {
      ref2.value = '';
    }
  }

  save(image: any, imagev: any) {
    if (image.files.length > 0) {
      this.user.image = image.files[0];

    }

    this._userService.update(this.user).subscribe(res => {
    }, error => {
    }, () => {
      this._userService.logout()
    });
    this.reset(image, imagev);

  }

  reset(image: any, imagev: any) {
    if (image.files.length > 0) {
      image.files = null;
    }
    imagev.value = "";
    this.ngOnInit();
  }

}
