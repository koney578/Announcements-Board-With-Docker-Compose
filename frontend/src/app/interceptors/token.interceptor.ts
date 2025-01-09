import { HttpInterceptorFn } from '@angular/common/http';
import {Router} from "@angular/router";
import {inject} from "@angular/core";
import {TokenService} from "../services/token/token.service";

export const tokenInterceptor: HttpInterceptorFn = (req, next) => {
  const tokenService: TokenService = inject(TokenService);
  const router: Router = inject(Router);

  const token: string = tokenService.token;
  if (token) {
    if (tokenService.isTokenExpired()) {
      tokenService.removeToken();
      router.navigateByUrl('/login');
      return next(req);
    }

    const headers = req.headers.append('Authorization', `Bearer ${token}`);
    req = req.clone({
      headers
    });
  }

  return next(req);
};
