import {HttpInterceptorFn} from '@angular/common/http';
import {inject} from '@angular/core';
import {TokenService} from '../../features/auth/services/token.service';

export const authInterceptor: HttpInterceptorFn = (request, next) => {

  const tokenService = inject(TokenService);
  const token = tokenService.getToken();

  if (token) {
    const clonedRequest = request.clone({
      setHeaders: {
        Authorization: `Bearer ${token}`
      }
    });

    return next(clonedRequest);

  }

  return next(request);

}

