import {Component, OnInit} from "@angular/core";
import {Router, ActivatedRoute} from "@angular/router";
import {AuthService} from "../../../services/auth.service";
import {User} from "../../../domain/User";
import {PlayerService} from "../../../services/player.service";
import {UserService} from "../../../services/user.service";

@Component({
  selector: 'm-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {
  searchKey: string = '';
  user: User = new User();

  constructor(private _activatedRoute: ActivatedRoute, private _router: Router, private _authService: AuthService, private _playerService: PlayerService, private _userService: UserService) {
  }

  ngOnInit() {
    this.user = this._authService.getAuth();
  }

  search() {
    if (this.searchKey.trim() != "") this._router.navigate(['/search', this.searchKey]);
  }

  logout() {
    this._playerService.stop();
    this._userService.logout();
  }
}
