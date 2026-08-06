import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {TokenService} from './token.service';
import {LoginRequest, LoginResponse} from '../models/login.model';
import {RegisterRequest, RegisterResponse} from '../models/register.model';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private apiURL = 'http://localhost:8080/api/auth';

  constructor(
    private http: HttpClient,
    private tokenService: TokenService
  ) { }

  login(request: LoginRequest) {
    return this.http.post<LoginResponse>(
      `${this.apiURL}/login`,
      request
    )
  }

  saveSession(response: LoginResponse) {
    this.tokenService.saveToken(
      response.token
    );
  }

  logout() {
    this.tokenService.removeToken();
  }

  register(request: RegisterRequest) {
    return this.http.post<RegisterResponse>(
      `${this.apiURL}/register`,
      request
    )
  }


}
