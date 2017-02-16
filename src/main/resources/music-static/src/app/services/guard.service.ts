import {Injectable} from "@angular/core";
import {CanActivate, RouterStateSnapshot, ActivatedRouteSnapshot, Router} from "@angular/router";
import {Observable} from "rxjs";
import {AuthService} from "./auth.service";
import {DefaultService} from "./default.service";

@Injectable()
export class GuardService implements CanActivate {
  securedRoutes: string[] = ['home', 'profile', 'upload', 'search', 'settings'];
  authRedirectRoutes: string[] = ['signin', 'signup', 'active', ''];

  constructor(private _router: Router, private _authService: AuthService, private _defaultService: DefaultService) {

  }

  canActivateImpl(path: string): Observable<boolean>|Promise<boolean>|boolean {
    if (this._defaultService.mock == true)return true;
    if (this.securedRoutes.indexOf(path) != -1) {
      if (this._authService.isAuth()) {
        return true;
      }
      else {
        this._router.navigate(['/signin']);
        return false;
      }
    }
    else if (this.authRedirectRoutes.indexOf(path) != -1) {
      if (this._authService.isAuth()) {
        this._router.navigate(['/home']);
        return false;
      }
      else {
        return true;
      }
    }
    else {
      return true;
    }
  }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<boolean>|Promise<boolean>|boolean {
    return this.canActivateImpl(this._defaultService.lastUrl);
  }
}
