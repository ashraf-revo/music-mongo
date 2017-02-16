import {Injectable} from "@angular/core";
import {Song} from "../domain/Song";
import {Http, Response} from "@angular/http";
import {Observable} from "rxjs";
import {DefaultService} from "./default.service";
import {AuthService} from "./auth.service";
import {SearchCriteria} from "../domain/SearchCriteria";
import {PageableSong} from "../domain/PageableSong";
import {Like} from "../domain/Like";
import {View} from "../domain/View";

@Injectable()
export class SongService {
  private url: string = "";

  constructor(private _http: Http, private _defaultService: DefaultService, private _authService: AuthService) {
    this.url = this._defaultService.url + "/api/song"
  }

  songs(): Observable<Song[]> {
    if (!this._authService.isAuth() || this._defaultService.mock)return Observable.empty<Song[]>();
    return this._http.get(this.url).map(res => res.json())
  }

  songsSearch(searchCriteria: SearchCriteria): Observable<PageableSong> {
    if (!this._authService.isAuth() || this._defaultService.mock)return Observable.empty<PageableSong>();
    return this._http.post(this.url + "/search", searchCriteria).map(res => res.json())
  }

  like(song: Song): Observable<Like> {
    let like: Like = new Like();
    like.user = this._authService.getAuth();
    like.song = song;
    return this._http.post(this.url + "/like", like).map(res => res.json());
  }

  unlike(like: Like): Observable<Response> {
    return this._http.post(this.url + "/unlike", like);
  }

  view(song: Song): Observable<Response> {
    let view = new View();
    view.user = this._authService.getAuth();
    let s: Song = new Song();
    s.id = song.id;
    view.song = s;
    return this._http.post(this.url + "/view", view)
  }

}
