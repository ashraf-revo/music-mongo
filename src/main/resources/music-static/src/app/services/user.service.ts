import {Injectable} from "@angular/core";
import {Observable} from "rxjs";
import {User} from "../domain/User";
import {Http, Headers, RequestOptions, Response} from "@angular/http";
import {DefaultService} from "./default.service";
import {AuthService} from "./auth.service";
import {GuardService} from "./guard.service";
import {View} from "../domain/View";
import {Like} from "../domain/Like";
import {Song} from "../domain/Song";

@Injectable()
export class UserService {
  private url: string = "";

  constructor(private _http: Http, private _defaultService: DefaultService, private _authService: AuthService, private _guardService: GuardService) {
    this.url = this._defaultService.url + "/api/user"
  }

  user(): Observable<User> {
    if (this._defaultService.mock) {
      let user: User = new User();
      user.id = "1";
      user.email = "email";
      user.name = "jjjjjjj";
      return Observable.of(user);
    }
    else {
      return this._http.get(this.url).map(res => res.json());
    }
  }

  userId(id: string): Observable<User> {
    if (this._defaultService.mock) {
      let user: User = new User();
      user.id = "1";
      user.email = "email";
      user.name = "jjjjjjj";
      return Observable.of(user);
    }
    else {
      return this._http.get(this.url + "/" + id).map(res => res.json());
    }
  }

  login(user: User, path: string, fun: Function) {
    let data: string = "username=" + user.email + "&password=" + user.password + "&remember-me=" + user.remember;
    let userObservable: Observable<User> = this._http.post(this._defaultService.url + "/login", data, new RequestOptions({headers: new Headers({"Content-Type": 'application/x-www-form-urlencoded'})})).map(res => res.json());
    this.loginRoute(userObservable, fun);
  }

  logout() {
    if (this._authService.isAuth()) this.logoutRoute(this._http.post(this._defaultService.url + "/logout", {}));
  }

  loginRoute(userObservable: Observable<User>, fun: Function) {
    userObservable.subscribe(user => {
      this._authService.setAuth(user);
      this._guardService.canActivateImpl(this._defaultService.lastUrl);
    }, error => {
      this._authService.remove();
      this._guardService.canActivateImpl(this._defaultService.lastUrl);
      fun(error._body);
    }, () => {
    })
  }

  logoutRoute(userObservable: Observable<Response>) {
    userObservable.subscribe(u => {
    }, error => {
    }, () => {
      this._authService.remove();
      this._guardService.canActivateImpl(this._defaultService.lastUrl);
    })
  }

  save(user: User): Observable<Response> {
    return this._http.post(this.url, this.userToFormData(user))
  }

  update(user: User): Observable<Response> {
    return this._http.post(this.url + "/update", this.userToFormData(user))
  }

  songsTo(it: string): Observable<Song[]> {
    if (!this._authService.isAuth() || this._defaultService.mock)return Observable.empty<Song[]>();
    return this._http.get(this.url + "/" + it+"/songs").map(res => res.json())
  }

  likesTo(it: string): Observable<Song[]> {
    if (!this._authService.isAuth() || this._defaultService.mock)return Observable.empty<Song[]>();
    return this._http.get(this.url + "/" + it + "/likes").map(res => res.json())

  }

  viewsTo(it: string): Observable<Song[]> {
    if (!this._authService.isAuth() || this._defaultService.mock)return Observable.empty<Song[]>();
    return this._http.get(this.url + "/" + it + "/views").map(res => res.json())
  }


  private userToFormData(user: User): FormData {
    let form: FormData = new FormData();
    if (user.id != null) form.append("id", user.id);
    if (user.name != null) form.append("name", user.name);
    if (user.phone != null) form.append("phone", user.phone);
    if (user.info != null) form.append("info", user.info);
    if (user.email != null) form.append("email", user.email);
    if (user.password != null) form.append("password", user.password);
    if (user.currentPassword != null) form.append("currentPassword", user.currentPassword);
    if (user.image != null) form.append("image", user.image);
    return form;
  }

  active(id: string): Observable<Response> {
    return this._http.get(this.url + "/" + id + "/active")
  }
}
