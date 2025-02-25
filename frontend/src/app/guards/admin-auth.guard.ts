import {CanActivateFn, Router} from '@angular/router';
import {inject} from "@angular/core";
import {TokenService} from "../services/token/token.service";

export const adminAuthGuard: CanActivateFn = (route, state) => {
  const tokenService: TokenService = inject(TokenService);
  const router: Router = inject(Router);

  if (tokenService.token && tokenService.isAdmin()) {
    return true;
  }

  router.navigateByUrl('/');
  return false;
};
