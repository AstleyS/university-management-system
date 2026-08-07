import {Role} from '../../../core/enums/Role';

export interface LoginRequest {

  username: string;
  password: string;

}

export interface LoginResponse {
  username: string,
  role: Role[],
  token: string;
}
