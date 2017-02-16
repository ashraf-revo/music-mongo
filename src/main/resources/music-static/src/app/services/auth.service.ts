import {Injectable} from "@angular/core";
import {User} from "../domain/User";
import {LocalStorageService} from "angular-2-local-storage";
import {DefaultService} from "./default.service";
@Injectable()
export class AuthService {
  private userKey: string = "user";

  constructor(private _localStorageService: LocalStorageService, private _defaultService: DefaultService) {
  }

  isAuth(): boolean {
    if (this._defaultService.mock)return true;
    return this.getAuth() != null;
  }

  setAuth(user: User) {
    this._localStorageService.set(this.userKey, user);
  }

  getAuth(): User {
    return <User>this._localStorageService.get(this.userKey);
  }

  remove() {
    this._localStorageService.remove(this.userKey)
  }
}
