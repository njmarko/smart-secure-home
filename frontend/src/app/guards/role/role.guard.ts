import { Injectable } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import {
  ActivatedRouteSnapshot,
  CanActivate,
  Router,
  RouterStateSnapshot,
  UrlTree,
} from '@angular/router';
import { Observable } from 'rxjs';
import { CurrentUserService } from '../../services/currrent-user-service/current-user.service';
import { AuthResponse } from 'src/app/model/auth/AuthResponse';

@Injectable({
  providedIn: 'root',
})
export class RoleGuard implements CanActivate {
  constructor(
    private currentUserService: CurrentUserService,
    private router: Router,
    private snackBar: MatSnackBar
  ) {}

  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ):
    | Observable<boolean | UrlTree>
    | Promise<boolean | UrlTree>
    | boolean
    | UrlTree {
    const roles: string[] = route.data['roles'];
    const user: AuthResponse | null = this.currentUserService.getCurrentUser();
    if (!user) {
      this.router.navigate(['/auth/login'], { queryParams: { to: state.url } });
      return false;
    }
    let hasAccessRights = false;
    roles.forEach((role) => {
      if (user.authorities.includes(role)) {
        hasAccessRights = true;
      }
    });
    if (hasAccessRights) {
      return true;
    } else {
      // TODO: Maybe send on forbidden page or something like that, left snack bar just to be sure it works
      this.snackBar.open(
        `You do not have required priviledges to access this resource!`,
        'Dismiss',
        { duration: 5000, verticalPosition: 'top' }
      );
      this.router.navigate(['']);
      return false;
    }
  }
}
