import {Role} from '../../../core/enums/Role';

export interface RegisterRequest {

  username: string;
  password: string;
  roles: Set<Role>

}

export interface RegisterResponse {

  id: number;
  username: string;
  role: Set<Role>

}
