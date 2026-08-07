import { Injectable } from '@angular/core';
import { LoginResponse } from '../models/login.model';

@Injectable({
  providedIn: 'root'
})
export class TokenService {

  private readonly TOKEN_KEY = 'access_token';
  private readonly USER_KEY = 'current_user';

  saveToken(token: string) {
    localStorage.setItem(
      this.TOKEN_KEY,
      token
    );
  }

  getToken(): string | null {
    return localStorage.getItem(
      this.TOKEN_KEY
    );
  }

  removeToken(){
    localStorage.removeItem(
      this.TOKEN_KEY
    );
  }

  saveUser(user: Omit<LoginResponse, 'token'>) {
    localStorage.setItem(
      this.USER_KEY,
      JSON.stringify(user)
    );
  }

  getCurrentUser(): Omit<LoginResponse, 'token'> | null {
    const user = localStorage.getItem(this.USER_KEY);
    return user ? JSON.parse(user) : null;
  }

  removeUser() {
    localStorage.removeItem(this.USER_KEY);
  }

  isLoggedIn(): boolean {
    return this.getToken() !== null;
  }
}
