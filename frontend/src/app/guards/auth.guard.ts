import {CanActivateFn, Router} from '@angular/router';
import {TokenService} from "../services/token/token.service";
import {inject} from "@angular/core";

export const authGuard: CanActivateFn = (route, state) => {
  const tokenService: TokenService = inject(TokenService);
  const router: Router = inject(Router);

  if (tokenService.token) {
    return true;
  }

  router.navigateByUrl('/login');
  return false;
};
