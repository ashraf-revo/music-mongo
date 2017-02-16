import {Component, OnInit} from "@angular/core";
import {ActivatedRoute, Params, Router} from "@angular/router";
import {UserService} from "../../services/user.service";

@Component({
  selector: 'm-active',
  templateUrl: './active.component.html',
  styleUrls: ['./active.component.css']
})
export class ActiveComponent implements OnInit {
  success: number = -1;

  constructor(private _activatedRoute: ActivatedRoute, private _userService: UserService, private _router: Router) {

  }

  ngOnInit() {
    this._activatedRoute.params.map((it: Params) => it['id'])
      .flatMap(id => this._userService.active(id))
      .subscribe((res) => this.success = 1, (error) => this.success = 0);
  }
}
