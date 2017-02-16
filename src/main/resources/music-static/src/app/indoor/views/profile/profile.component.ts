import {Component, OnInit, OnDestroy} from "@angular/core";
import {Song} from "../../../domain/Song";
import {Router, ActivatedRoute, Params} from "@angular/router";
import {Subscription, Observable} from "rxjs";
import {User} from "../../../domain/User";
import {UserService} from "../../../services/user.service";

@Component({
  selector: 'm-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit,OnDestroy {
  c: string = 'uploaded';
  songs: Song[] = [];
  views: Song[] = [];
  likes: Song[] = [];
  user: User = new User();
  subscription1: Subscription;
  subscription2: Subscription;
  subscription3: Subscription;
  subscription4: Subscription;

  constructor(private _router: Router, private _activatedRoute: ActivatedRoute, private _userService: UserService) {
  }

  ngOnInit() {
    let routerFilter = this._activatedRoute.params.map((it: Params) => <string>it['id']).filter((it: string) => it.trim() != "");
    this.subscription1 = routerFilter.flatMap((it: string) => {
      return <Observable<Song[]>>this._userService.songsTo(it);
    })
      .subscribe((it: Song[]) => {
        this.songs = it;
      });
    this.subscription2 = routerFilter.flatMap((it: string) => {
      return <Observable<Song[]>>this._userService.likesTo(it);
    })
      .subscribe((it: Song[]) => {
        this.likes = it
      });
    this.subscription3 = routerFilter.flatMap((it: string) => {
      return <Observable<Song[]>>this._userService.viewsTo(it);
    })
      .subscribe((it: Song[]) => {
        this.views = it
      });
    this.subscription4 = routerFilter
      .flatMap((it: string) => {
        return <Observable<User>>this._userService.userId(it);
      })
      .subscribe((it: User) => {
        this.user = it;
      });
  }

  ngOnDestroy(): void {
    this.subscription1.unsubscribe();
    this.subscription2.unsubscribe();
    this.subscription3.unsubscribe();
    this.subscription4.unsubscribe();
  }
}
