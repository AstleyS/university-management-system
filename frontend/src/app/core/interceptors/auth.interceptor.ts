import {HttpInterceptorFn} from '@angular/common/http';
import {inject} from '@angular/core';
import {TokenService} from '../../features/auth/services/token.service';
import {Router} from '@angular/router';
import {catchError, throwError} from 'rxjs';
import {AuthService} from '../../features/auth/services/auth.service';

export const authInterceptor: HttpInterceptorFn = (request, next) => {

  const tokenService = inject(TokenService);
  const router = inject(Router);
  const authService = inject(AuthService);
  const token = tokenService.getToken();

  if (token) {
    const clonedRequest = request.clone({
      setHeaders: {
        Authorization: `Bearer ${token}`
      }
    });

    return next(clonedRequest).pipe(
      catchError(error => {
        if (error.status === 401 || error.status === 403) {
          authService.logout();
          router.navigate(['/login']);
        }
        return throwError(() => error);
      })
    );

  }

  return next(request);

}

